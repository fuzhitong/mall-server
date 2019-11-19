package cn.enjoy.mall.service;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;
import cn.enjoy.mall.vo.KillOrderVo;
import cn.enjoy.mall.vo.OrderCreateVo;

public interface IOrderService {
    Integer createOrder(OrderCreateVo crderCreateVo,String userId);
    Integer killOrder(int addressId, KillGoodsSpecPriceDetailVo killGoods, String userId);
    Integer killOrder(KillOrderVo killOrderVo);
    GridModel<Order> searchListPage(Integer type, String keywords, int page, int pageSize,String userId);
    Order selectOrderDetail(Integer orderId);
    Order selectMyOrderDetail(Integer orderId,String userId);
    void cancel(Integer orderId);
    void selfCancel(Integer orderId,String userId);
    void autoCancelExpiredOrder(); //自动取消过期订单
    Integer queryOrderNum(Integer type,String userId);
    void confirmReceiveGoods(Integer orderId ,String userId);
}
