package cn.enjoy.mall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.mall.dao.OrderActionMapper;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderAction;
import cn.enjoy.mall.service.IOrderActionService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class OrderActionServiceImpl implements IOrderActionService {
    @Resource
    private OrderActionMapper orderActionMapper;

    @Override
    public void save(Order order, String action, String userId) {
        this.save(order, action, userId, null);
    }
    @Override
    public int savePre(Order order, Map map , String action, String userId, String remark) {
        OrderAction orderAction = new OrderAction();
        orderAction.setActionUser(userId);
        orderAction.setLogTime(System.currentTimeMillis());
        orderAction.setOrderId(order.getOrderId());
        orderAction.setOrderStatus(order.getOrderStatus());
        orderAction.setPayStatus(order.getPayStatus());
        orderAction.setShippingStatus(order.getShippingStatus());
        orderAction.setStatusDesc(action);
        orderAction.setActionNote(remark);
        if(map !=null&&map.get("trade_type")!=null){
            orderAction.setTradeType(map.get("trade_type").toString());
        }
        if(map !=null&&map.get("prepay_id")!=null){
            orderAction.setPrepayId(map.get("prepay_id").toString());
        }
        if(map !=null&&map.get("code_url")!=null){
            orderAction.setCodeUrl(map.get("code_url").toString());
        }
        orderActionMapper.insert(orderAction);
        return orderAction.getActionId();
    }

    @Override
    public int updatePre(int actionId,Map map ) {
        OrderAction orderAction = orderActionMapper.selectByPrimaryKey(actionId);
        if(map.get("trade_type")!=null){
            orderAction.setTradeType(map.get("trade_type").toString());
        }
        if(map.get("prepay_id")!=null){
            orderAction.setPrepayId(map.get("prepay_id").toString());
        }
        if(map.get("code_url")!=null){
            orderAction.setCodeUrl(map.get("code_url").toString());
        }
        orderActionMapper.updateByPrimaryKey(orderAction);
        return orderAction.getActionId();
    }


    @Override
    public void save(Order order, String action, String userId, String remark) {
        OrderAction orderAction = new OrderAction();
        orderAction.setActionUser(userId);
        orderAction.setLogTime(System.currentTimeMillis());
        orderAction.setOrderId(order.getOrderId());
        orderAction.setOrderStatus(order.getOrderStatus());
        orderAction.setPayStatus(order.getPayStatus());
        orderAction.setShippingStatus(order.getShippingStatus());
        orderAction.setStatusDesc(action);
        orderAction.setActionNote(remark);
        orderActionMapper.insert(orderAction);
    }

    @Override
    public OrderAction queryByPrepayId(String prepayId) {
        OrderAction orderAction = new OrderAction();
        orderAction = orderActionMapper.queryByPrepayId(prepayId);
        return orderAction;
    }

    @Override
    public List<OrderAction> queryByOrderId(Integer orderId) {
        return orderActionMapper.queryByOrderId(orderId);
    }
}
