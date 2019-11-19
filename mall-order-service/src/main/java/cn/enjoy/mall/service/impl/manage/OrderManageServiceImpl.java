package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.mall.dao.OrderGoodsMapper;
import cn.enjoy.mall.service.manage.IOrderManageService;
import cn.enjoy.core.utils.GridModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import cn.enjoy.mall.constant.PayStatus;
import cn.enjoy.mall.constant.ShippingStatus;
import cn.enjoy.mall.dao.OrderManageMapper;
import cn.enjoy.mall.dao.OrderMapper;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderGoods;
import cn.enjoy.mall.vo.OrderVo;
import cn.enjoy.mall.constant.OrderStatus;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ray
 * @date 2018/3/8.
 */
@Service
public class OrderManageServiceImpl implements IOrderManageService {


    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderManageMapper orderManageMapper;
    @Resource
    private OrderGoodsMapper orderGoodsMapper;


    @Override
    public GridModel<OrderVo> queryByPage(int page, int pageSize, OrderVo params) {
        PageBounds pageBounds = new PageBounds(page, pageSize);
        return new GridModel<>(orderManageMapper.queryByPage(params, pageBounds));
    }

    @Override
    public OrderVo queryOrderDetail(int orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public void save(OrderVo orderVo) {

    }

    @Override
    public void delete(short id) {

    }

    @Override
    public void deleteByIds(String[] ids) {

    }

    @Override
    public List<OrderGoods> selectGoodsByOrderId(Integer orderId) {
        return  orderGoodsMapper.selectByOrderId(orderId);
    }

    @Override
    public int update(Order order) {
        if(OrderStatus.CONFIRMED.getCode().equals(order.getOrderStatus())){
            order.setConfirmTime(System.currentTimeMillis()/1000);
        }
        if(PayStatus.PAID.getCode().equals(order.getPayStatus())){
            order.setPayTime(System.currentTimeMillis()/1000);
        }
        if(ShippingStatus.SHIPPED.getCode().equals(order.getShippingStatus())){
            order.setShippingTime(System.currentTimeMillis()/1000);
        }
        return orderMapper.updateByPrimaryKeySelective(order);
    }
}
