package cn.enjoy.mall.service.impl;

import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.constant.MallConstant;
import cn.enjoy.mall.constant.OrderStatus;
import cn.enjoy.mall.constant.PayStatus;
import cn.enjoy.mall.constant.ShippingStatus;
import cn.enjoy.mall.dao.OrderGoodsMapper;
import cn.enjoy.mall.dao.OrderMapper;
import cn.enjoy.mall.dao.UserAddressMapper;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderGoods;
import cn.enjoy.mall.model.SpecGoodsPrice;
import cn.enjoy.mall.model.UserAddress;
import cn.enjoy.mall.mongo.GoodsDao;
import cn.enjoy.mall.service.IOrderActionService;
import cn.enjoy.mall.service.IOrderService;
import cn.enjoy.mall.service.IShoppingCartService;
import cn.enjoy.mall.vo.*;
import cn.enjoy.users.annotation.Master;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  0|0|0 待支付
    0|0|1 已付款待配货
    1|0|1 已配货待出库
    1|1|1 待收货
    2|1|1 已完成
    4|1|1 已完成
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderGoodsMapper orderGoodsMapper;
    @Resource
    private UserAddressMapper userAddressMapper;
    @Resource
    private IShoppingCartService shoppingCartService;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private IOrderActionService orderActionService;
    @Resource
    private SequenceGenerator sequenceGenerator;

    @Transactional
    @Override
    public Integer createOrder(OrderCreateVo orderCreateVo, String userId) {
        //创建一个订单
        Order order = new Order();
        //从SequenceGenerator中获取订单的变化
        order.setOrderSn(sequenceGenerator.getOrderNo());
        order.setAddTime(System.currentTimeMillis());
        //设置订单的状态为未确定订单
        order.setOrderStatus(OrderStatus.UNCONFIRMED.getCode());
        //未支付
        order.setPayStatus(PayStatus.UNPAID.getCode());
        //未发货
        order.setShippingStatus(ShippingStatus.UNSHIPPED.getCode());
        //获取发货地址
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(orderCreateVo.getAddressId());
        BeanUtils.copyProperties(userAddress,order);
        order.setUserId(userId);

        //新增订单
        orderMapper.insert(order);
        int orderId = order.getOrderId();
        BigDecimal totalAmount = new BigDecimal(0);
        //从mongodb的购物车中获取所购物品
        List<ShoppingGoodsVo> checkedGoodsList = shoppingCartService.findCheckedGoodsList(userId);
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for(ShoppingGoodsVo goodsAddVo : checkedGoodsList){
            GoodsVo goodsVo = goodsDao.findOneBySpecGoodsId(goodsAddVo.getSpecGoodsId());
            if(goodsVo == null ){
                throw new BusinessException("没有找到对应的商品["+goodsAddVo.getGoodsName()+"],可能已下架");
            }
            if(goodsVo.getBase().getIsOnSale() == false){
                throw new BusinessException("对不起，商品["+goodsAddVo.getGoodsName()+"]已下架");
            }
            List<SpecGoodsPrice> machedSpecGoodsPriceList = goodsVo.getSpecGoodsPriceList().stream()
                    .filter(specGoodsPrice -> specGoodsPrice.getId().intValue() == goodsAddVo.getSpecGoodsId().intValue()).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(machedSpecGoodsPriceList)){
                throw new BusinessException("没有找到对应的商品["+goodsAddVo.getGoodsName()+"],可能已下架");
            }
            SpecGoodsPrice specGoodsPrice = machedSpecGoodsPriceList.get(0);
            //更新商品库存
            goodsVo.getSpecGoodsPriceList().remove(specGoodsPrice);
            specGoodsPrice.setStoreCount(specGoodsPrice.getStoreCount() - goodsAddVo.getNum().shortValue());
            goodsVo.getSpecGoodsPriceList().add(specGoodsPrice);
            //把当前订单信息保存到mongodb
            goodsDao.save(goodsVo);

            //创建的订单商品
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderId);
            BeanUtils.copyProperties(goodsVo.getBase(),orderGoods);
            orderGoods.setGoodsNum(goodsAddVo.getNum().shortValue());
            orderGoods.setGoodsPrice(specGoodsPrice.getPrice());
            orderGoods.setBarCode(specGoodsPrice.getBarCode());
            orderGoods.setSpecKey(specGoodsPrice.getKey());
            orderGoods.setSpecKeyName(specGoodsPrice.getKeyName());
            orderGoods.setSpecGoodsId(specGoodsPrice.getId());
            if(CollectionUtils.isEmpty(specGoodsPrice.getSpecGoodsImagesList())){
                orderGoods.setOriginalImg(goodsVo.getBase().getOriginalImg());
            }else{
                orderGoods.setOriginalImg(specGoodsPrice.getSpecGoodsImagesList().get(0).getSrc());
            }
            orderGoodsList.add(orderGoods);
            totalAmount = totalAmount.add(specGoodsPrice.getPrice().multiply(new BigDecimal(goodsAddVo.getNum())));
        }
        order.setGoodsPrice(totalAmount);
        order.setShippingPrice(new BigDecimal(0));
        order.setOrderAmount(totalAmount.add(order.getShippingPrice()));
        order.setTotalAmount(totalAmount.add(order.getShippingPrice()));

        //修改订单
        orderMapper.updateByPrimaryKeySelective(order);

        //保存订单产品信息
        orderGoodsMapper.insertBatch(orderGoodsList);
        //清除购物车中已下单的商品
        shoppingCartService.removeCheckedGoodsList(userId);
        //订单日志
        orderActionService.save(order,"创建订单",userId);

        return orderId;
    }

    @Transactional
    @Master
    public Integer killOrder(int addressId, KillGoodsSpecPriceDetailVo killGoods, String userId) {
        //创建一个订单
        Order order = new Order();
        //从SequenceGenerator中获取订单的变化
        order.setOrderSn(sequenceGenerator.getOrderNo());
        order.setAddTime(System.currentTimeMillis());
        //设置订单的状态为未确定订单
        order.setOrderStatus(OrderStatus.UNCONFIRMED.getCode());
        //未支付
        order.setPayStatus(PayStatus.UNPAID.getCode());
        //未发货
        order.setShippingStatus(ShippingStatus.UNSHIPPED.getCode());
        //获取发货地址
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        BeanUtils.copyProperties(userAddress,order);
        order.setUserId(userId);

        //新增订单
        orderMapper.insert(order);
        int orderId = order.getOrderId();
        BigDecimal totalAmount = new BigDecimal(0);
        //从mongodb的购物车中获取所购物品
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        GoodsVo goodsVo = goodsDao.findOneBySpecGoodsId(killGoods.getSpecGoodsId());

        //创建的订单商品
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(orderId);
        BeanUtils.copyProperties(goodsVo.getBase(),orderGoods);
        orderGoods.setPromType(true);
        orderGoods.setPromId(killGoods.getId());
        orderGoods.setGoodsNum((short)1);
        orderGoods.setGoodsPrice(killGoods.getPrice());
        orderGoods.setSpecKey(killGoods.getKey());
        orderGoods.setSpecKeyName(killGoods.getKeyName());
        orderGoods.setSpecGoodsId(killGoods.getSpecGoodsId());
        orderGoods.setOriginalImg(killGoods.getOriginalImg());
        orderGoodsList.add(orderGoods);
        totalAmount = totalAmount.add(killGoods.getPrice());
        order.setGoodsPrice(totalAmount);
        order.setShippingPrice(new BigDecimal(0));
        order.setOrderAmount(totalAmount.add(order.getShippingPrice()));
        order.setTotalAmount(totalAmount.add(order.getShippingPrice()));

        //修改订单
        orderMapper.updateByPrimaryKeySelective(order);

        //保存订单产品信息
        orderGoodsMapper.insertBatch(orderGoodsList);
        //订单日志
        orderActionService.save(order,"创建秒杀订单",userId);

        return orderId;
    }

    @Transactional
    @Master
    @Override
    public Integer killOrder(KillOrderVo killOrderVo) {
        return this.killOrder(killOrderVo.getAddressId(),
                killOrderVo.getKillGoodsSpecPriceDetailVo(),killOrderVo.getUserId());
    }


    /**
     * 分页查询订单
     * @param type： 0-全部订单，1-全部有效订单，2-待支付，3-待收货，4-已关闭
     * @param keywords 订单号
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public GridModel<Order> searchListPage(Integer type,String keywords, int page, int pageSize,String userId) {
        String orderString = "add_time.desc";
        PageBounds pageBounds = new PageBounds(page, pageSize, com.github.miemiedev.mybatis.paginator.domain.Order.formString(orderString));
        PageList<Order> pageList =(PageList<Order>)orderMapper.queryByPage(type,keywords,pageBounds, userId);
        if(!pageList.isEmpty()){
            pageList.forEach(x -> x.setOrderGoodsList(orderGoodsMapper.selectByOrderId(x.getOrderId())));
        }
        return new GridModel<Order>(pageList);
    }

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    @Override
    public Order selectOrderDetail(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order!=null){
            List<OrderGoods> goodsList = orderGoodsMapper.selectByOrderId(orderId);
            order.setOrderGoodsList(goodsList);
        }
        return order;
    }

    @Override
    public Order selectMyOrderDetail(Integer orderId, String userId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order!=null){
            if(StringUtils.isEmpty(userId) || !userId.equals(order.getUserId())){
                  return null;
            }
            List<OrderGoods> goodsList = orderGoodsMapper.selectByOrderId(orderId);
            order.setOrderGoodsList(goodsList);
        }
        return order;
    }

    @Transactional
    @Override
    public void cancel(Integer orderId ) {
        cancel(orderId,null,false);
    }

    private void cancel(Integer orderId ,String userId,boolean checkUser) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order!=null) {
            if(checkUser){
                if (StringUtils.isEmpty(userId) || !userId.equals(order.getUserId())) {
                    throw new BusinessException("订单不存在");
                }
            }
        }else{
            throw new BusinessException("订单不存在");
        }
        order.setOrderStatus(OrderStatus.CANCELED.getCode());
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByOrderId(orderId);
        for(OrderGoods orderGoods : orderGoodsList ){
            GoodsVo goodsVo = goodsDao.findOneById(orderGoods.getGoodsId());
            if(goodsVo!=null){
                List<SpecGoodsPrice> specGoodsPriceList = goodsVo.getSpecGoodsPriceList();
                specGoodsPriceList.forEach(x -> x.setStoreCount(x.getStoreCount()+orderGoods.getGoodsNum()));
                goodsVo.setSpecGoodsPriceList(specGoodsPriceList);
                goodsDao.save(goodsVo);
            }
        }
        orderMapper.updateByPrimaryKeySelective(order);
        //订单日志
        orderActionService.save(order,checkUser==true?"取消订单":"自动取消订单",userId);
    }
    @Transactional
    @Override
    public void selfCancel(Integer orderId, String userId) {
        cancel(orderId,userId,true);
    }

    @Transactional
    @Override
    public void autoCancelExpiredOrder() {
        List<Order> expiredOrderList = orderMapper.selectExpiredOrder(MallConstant.EXPIRED_TIME_INTERVAL);
        if(!CollectionUtils.isEmpty(expiredOrderList)) {
            for (Order order : expiredOrderList) {
                cancel(order.getOrderId());
            }
        }
    }
    /**
     * 查询各类型的订单
     * @param type 0-全部订单，1-全部有效订单，2-待支付，3-待收货，4-已关闭
     * @return
     */
    @Override
    public Integer queryOrderNum(Integer type,String userId) {
        return orderMapper.selectOrderNum(type,userId);
    }

    @Transactional
    @Override
    public void confirmReceiveGoods(Integer orderId ,String userId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order == null) {
            throw new BusinessException("订单不存在");
        }else{
            if (StringUtils.isEmpty(userId) || !userId.equals(order.getUserId())) {
                throw new BusinessException("订单不存在");
            }
        }
        order.setOrderStatus(OrderStatus.RECEIVED.getCode());
        order.setReceiveTime(System.currentTimeMillis());
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByOrderId(orderId);
        for(OrderGoods orderGoods : orderGoodsList ){
            GoodsVo goodsVo = goodsDao.findOneById(orderGoods.getGoodsId());
            if(goodsVo!=null){
                List<SpecGoodsPrice> specGoodsPriceList = goodsVo.getSpecGoodsPriceList();
                specGoodsPriceList.forEach(x -> x.setStoreCount(x.getStoreCount()+orderGoods.getGoodsNum()));
                goodsVo.setSpecGoodsPriceList(specGoodsPriceList);
                goodsDao.save(goodsVo);
            }
        }
        orderMapper.updateByPrimaryKeySelective(order);
        //订单日志
        orderActionService.save(order,"确认收货",userId);
    }

    /**
     * 根据类型获取订单状态
     * @param type
     * @return
     */
    private Integer getOrderStatusByType(Integer type){
        Integer orderStatus = null;
        if(type == 0){
            orderStatus = null;
        }else if(type == 1){
             orderStatus = 99;
        }else if(type == 2){
            orderStatus = OrderStatus.CONFIRMED.getCode();
        }else if(type == 3) {
            orderStatus =  OrderStatus.CONFIRMED.getCode();
        }else if(type == 4){
            orderStatus =  OrderStatus.CANCELED.getCode();
        }
        return orderStatus;
    }
     /**
     * 根据类型获取支付状态
     * @param type
     * @return
     */
    private Integer getPayStatusByType(Integer type){
        Integer payStatus = null;
        if(type == 0){
            payStatus = null;
        }else if(type == 1){
            payStatus = null;
        }else if(type == 2){
            payStatus = PayStatus.UNPAID.getCode();
        }else if(type == 3) {
            payStatus =  PayStatus.PAID.getCode();
        }else if(type == 4){
            payStatus = null;
        }
        return payStatus;
    }
}
