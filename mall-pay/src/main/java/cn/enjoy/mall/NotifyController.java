package cn.enjoy.mall;


import cn.enjoy.mall.service.IPayService;
import cn.enjoy.mall.wxsdk.WXPay;
import cn.enjoy.mall.wxsdk.WXPayUtil;
import cn.enjoy.mall.wxsdk.WxPayConfigImpl;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;


/**
 */
@Controller
//http请求控制类  Contoller
public class NotifyController {
    @Reference
    private IPayService payService;
    @Autowired
    WxPayConfigImpl wxPayConfig;
    @Autowired
    WXPay wxPay;
    @Value("${wx.appId}")
    private String appId = "";
    @Value("${wx.mchId}")
    private String mchId = "";
    @Value("${wx.key}")
    private String partnerKey = "";
    @Value("${wx.certPath}")
    private String certPath = "";
    @Value("${wx.notify_url}")
    private String notify_url = "http://www.weixin.qq.com/wxpay/pay.php";

    @RequestMapping(value = "/wx/notify", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String payNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader reader = null;

        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        String result_code = "";
        String return_code = "";
        String out_trade_no = "";
        map =    WXPayUtil.xmlToMap(xmlString);
        result_code = map.get("result_code");
        return_code = map.get("return_code");
        if(return_code.equals("SUCCESS")){
            if(result_code.equals("SUCCESS")){
                String payResult = payService.updateByActionId(map.get("out_trade_no"));
                return payResult;
            }
        }
        return "fail";

    }

}

