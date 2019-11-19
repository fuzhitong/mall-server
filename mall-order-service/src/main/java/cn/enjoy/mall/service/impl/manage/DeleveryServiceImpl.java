package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.mall.model.OrderAction;
import cn.enjoy.mall.service.manage.IDeliveryService;
import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.mall.constant.ShippingStatus;
import cn.enjoy.mall.dao.DeliveryDocMapper;
import cn.enjoy.mall.dao.OrderActionMapper;
import cn.enjoy.mall.dao.OrderGoodsMapper;
import cn.enjoy.mall.dao.OrderMapper;
import cn.enjoy.mall.model.DeliveryDoc;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderGoods;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ray
 * @date 2018/3/22.
 */
@Service
public class DeleveryServiceImpl implements IDeliveryService {

    @Resource
    private DeliveryDocMapper deliveryDocMapper;
    @Resource
    private OrderGoodsMapper orderGoodsMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderActionMapper orderActionMapper;

    @Override
    public List<DeliveryDoc> queryDeliveryDocByOrderId(int orderId) {
        return deliveryDocMapper.selectByOrderId(orderId);
    }

    @Override
    @Transactional
    public void shipping(DeliveryDoc deliveryDoc, List<Integer> orderGoodsIds) {
        Order order = orderMapper.selectByPrimaryKey(deliveryDoc.getOrderId());
        // 1.写tp_delivery_doc
        deliveryDoc.setUserId(order.getUserId());
        deliveryDoc.setCreateTime((int) (System.currentTimeMillis()/1000));
        deliveryDocMapper.insertSelective(deliveryDoc);
        // 2.修改tp_order_goods.delivery_id, is_send=1
        for(Integer orderGoodsId:orderGoodsIds){
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setRecId(orderGoodsId);
            orderGoods.setIsSend(true);
            orderGoods.setDeliveryId(deliveryDoc.getId());
            orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
        }
        // 3.修改tp_order.shipping_status 全部商品都是已发货则为1，部分发货则为2
        order.setShippingTime(System.currentTimeMillis()/1000);
        int count = orderGoodsMapper.countNotSendGoodsByOrderId(deliveryDoc.getOrderId());
        if(count > 0) {
            order.setShippingStatus(ShippingStatus.PARTIAL_SHIPPED.getCode());
        } else {
            order.setShippingStatus(ShippingStatus.SHIPPED.getCode());
        }
        order.setShippingCode(deliveryDoc.getShippingCode());
        order.setShippingName(deliveryDoc.getShippingName());
        orderMapper.updateByPrimaryKeySelective(order);
        // 4.写订单日志

        this.log(order, "发货", deliveryDoc.getAdminId(), "运单号：" + deliveryDoc.getInvoiceNo());

    }

    private void log(Order order, String action, String userId, String remark) {
        OrderAction orderAction = new OrderAction();
        orderAction.setActionUser(userId);
        orderAction.setLogTime(System.currentTimeMillis()/1000);
        orderAction.setOrderId(order.getOrderId());
        orderAction.setOrderStatus(order.getOrderStatus());
        orderAction.setPayStatus(order.getPayStatus());
        orderAction.setShippingStatus(order.getShippingStatus());
        orderAction.setStatusDesc(action);
        orderAction.setActionNote(remark);
        orderActionMapper.insert(orderAction);
    }
}
