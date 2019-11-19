package cn.enjoy.mall.service;

import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderAction;

import java.util.List;
import java.util.Map;

public interface IOrderActionService {
    void save(Order order, String actinNote, String userId);
    int savePre(Order order, Map map , String action, String userId, String remark);

    /**
     * 写订单日志
     * @param order
     * @param action 本次做的操作，对应status_desc
     * @param userId
     * @param remark 备注，对应action_note
     */
    void save(Order order, String action, String userId, String remark);

    OrderAction queryByPrepayId(String prepayId);
   int updatePre(int actionId,Map map ) ;

    List<OrderAction> queryByOrderId(Integer orderId);
}
