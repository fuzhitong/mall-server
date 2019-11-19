package cn.enjoy.mall.wxsdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
@Service
public class WxPayConfigImpl extends WXPayConfig {

    private byte[] certData;
    private static WxPayConfigImpl INSTANCE;

    //公众号AppID
    @Value("${wx.appId}")
    private String appId = "";
    //商户号
    @Value("${wx.mchId}")
    private String mchId = "";
    //key
    @Value("${wx.key}")
    private String key = "";

    private WxPayConfigImpl() throws Exception {
//        String certPath = "D://CERT/common/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }

    public static WxPayConfigImpl getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WxPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WxPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return this.appId;
    }

    public String getMchID() {
        return this.mchId;
    }

    public String getKey() {
        return this.key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }

}