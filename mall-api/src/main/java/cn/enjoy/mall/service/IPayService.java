package cn.enjoy.mall.service;

import java.math.BigDecimal;
import java.util.Map;

public interface IPayService {
    Map<String, String> doPrePay(Integer orderId, String payCode, BigDecimal payAmount, String userId) ;
    String updateByActionId(String actionId) ;
    String queryByPrepayId(String prepayId) ;
    String doPay(Integer orderId, String payCode, BigDecimal payAmount,String userId) ;
}
