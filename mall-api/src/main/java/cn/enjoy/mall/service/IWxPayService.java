package cn.enjoy.mall.service;

import cn.enjoy.mall.model.Order;

import java.math.BigDecimal;
import java.util.Map;

public interface IWxPayService {
    Map<String, String> unifiedorder(String actionId , BigDecimal payAmount, String userId);
}
