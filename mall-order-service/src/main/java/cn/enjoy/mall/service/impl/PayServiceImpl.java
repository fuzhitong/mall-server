package cn.enjoy.mall.service.impl;

import cn.enjoy.mall.constant.PayStatus;
import cn.enjoy.mall.constant.PayType;
import cn.enjoy.mall.dao.OrderActionMapper;
import cn.enjoy.mall.dao.OrderMapper;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderAction;
import cn.enjoy.mall.service.IOrderActionService;
import cn.enjoy.mall.service.IPayService;
import cn.enjoy.mall.service.IWxPayService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements IPayService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderActionMapper orderActionMapper;
    @Resource
    private IOrderActionService orderActionService;
    @Reference
    private IWxPayService iWxPayService;
    @Transactional
    @Override
    public Map<String, String> doPrePay(Integer orderId, String payCode, BigDecimal payAmount, String userId){

        Order order = orderMapper.selectByPrimaryKey(orderId);
        Map<String, String> return_map = new HashMap<>();
        if(payAmount.compareTo(order.getOrderAmount())!=0){
            return_map.put("result_code","fail");
            return_map.put("return_msg","支付金额不正确");
            return return_map;
        }
        String payName = PayType.getDescByCode(payCode);
        if(StringUtils.isEmpty(payName)){
            return_map.put("result_code","fail");
            return_map.put("return_msg","支付方式不存在");
            return return_map;
        }
        if(order.getPayStatus() ==1){
            return_map.put("result_code","fail");
            return_map.put("return_msg","此订单已经付款完成！");
            return return_map;
        }
        order.setPayStatus(PayStatus.UNPAID.getCode());
        order.setPayCode(payCode);
        order.setPayName(PayType.getDescByCode(payCode));
        order.setPayTime(System.currentTimeMillis());
        orderMapper.updateByPrimaryKeySelective(order);
        int action_id = orderActionService.savePre(order,null,"微信-预支付订单",userId,"微信-预支付订单");

        Map<String, String> map = iWxPayService.unifiedorder(String.valueOf(action_id),payAmount,userId);
        orderActionService.updatePre(action_id,map);
        return map;
    }
    @Transactional
    @Override
    public String updateByActionId(String actionId) {
        OrderAction orderAction = orderActionMapper.selectByPrimaryKey(Integer.parseInt(actionId));
        Order order = orderMapper.selectByPrimaryKey(orderAction.getOrderId());
        order.setOrderId(order.getOrderId());
        order.setPayStatus(PayStatus.PAID.getCode());
        order.setPayTime(System.currentTimeMillis());
        orderMapper.updateByPrimaryKeySelective(order);

        orderAction.setPayStatus(1);
        orderAction.setLogTime(System.currentTimeMillis());
        orderActionMapper.updateByPrimaryKey(orderAction);
        return "SUCCESS";
    }

    @Transactional
    @Override
    public String doPay(Integer orderId, String payCode, BigDecimal payAmount,String userId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(payAmount.compareTo(order.getOrderAmount())!=0){
            return "支付金额不正确";
        }
        String payName = PayType.getDescByCode(payCode);
        if(StringUtils.isEmpty(payName)){
            return "支付方式不存在";
        }
        order.setPayStatus(PayStatus.PAID.getCode());
        order.setPayCode(payCode);
        order.setPayName(PayType.getDescByCode(payCode));
        order.setPayTime(System.currentTimeMillis());
        orderMapper.updateByPrimaryKeySelective(order);
        orderActionService.save(order,"支付成功",userId);
        return "success";
    }

    @Override
    public String queryByPrepayId(String prepayId) {

        OrderAction orderAction = orderActionService.queryByPrepayId(prepayId);
        if(orderAction.getPayStatus() != null){
            return orderAction.getPayStatus().toString();
        }else{
            return "0";
        }
    }
}
