package cn.enjoy.mall.service.manage;

import cn.enjoy.mall.model.DeliveryDoc;

import java.util.List;

/**
 * @author Ray
 * @date 2018/3/22.
 */
public interface IDeliveryService {

    List<DeliveryDoc> queryDeliveryDocByOrderId(int orderId);

    void shipping(DeliveryDoc deliveryDoc, List<Integer> orderGoodsIds);
}
