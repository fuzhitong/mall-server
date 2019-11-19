package cn.enjoy.mall;



import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.wxsdk.WXPay;
import cn.enjoy.mall.wxsdk.WXPayConstants;
import cn.enjoy.mall.wxsdk.WXPayUtil;
import cn.enjoy.mall.wxsdk.WxPayConfigImpl;
import cn.enjoy.mall.service.IWxPayService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 */
@Service
public class WxPayServiceImpl implements IWxPayService {
    @Autowired
    WxPayConfigImpl wxPayConfig;
    @Autowired
    WXPay wxPay;

    @Value("${wx.appId}")
    private String appId = "";
    @Value("${wx.mchId}")
    private String mchId = "";
    @Value("${wx.key}")
    private String key = "";
    @Value("${wx.certPath}")
    private String certPath = "";
    @Value("${wx.notify_url}")
    private String notify_url="";
    @Value("${wx.wap_url}")
    private String wap_url="";
    @Value("${wx.spbill_create_ip}")
    private String spbill_create_ip="";

    /**
     * 微信H5 支付
     * 注意：必须再web页面中发起支付且域名已添加到开发配置中
     */
    //微信预支付订单
    @Override
    public Map<String, String> unifiedorder(String actionId , BigDecimal payAmount, String userId){
        Map<String, String> reqData = new HashMap<>();
        try {
            WXPay wxPay = new WXPay(wxPayConfig);

            reqData.put("body","订单号:"+actionId);
            reqData.put("out_trade_no",actionId);

            reqData.put("total_fee",String.valueOf(payAmount.multiply(BigDecimal.valueOf(100)).intValue()));
            reqData.put("spbill_create_ip","127.0.0.1");
            reqData.put("notify_url",notify_url);
            reqData.put("trade_type","NATIVE");
            //调官方sdk统一下单方法
            Map<String, String> result = wxPay.unifiedOrder(reqData);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
