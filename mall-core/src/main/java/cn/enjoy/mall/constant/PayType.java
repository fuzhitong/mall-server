package cn.enjoy.mall.constant;

/**
 * 支付方式（alipay-支付宝支付、weixin-微信支付、cod-货到付款）
 */
public enum PayType {
    ALIPAY( "alipay", "支付宝支付"),
    WEIXIN( "weixin", "微信支付"),
    COD( "cod",  "货到付款");

    private String code;
    private String desc;

    PayType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(String code){
        for(PayType payType : PayType.values()){
            if(code.equals(payType.getCode())){
                return payType.getDesc();
            }
        }
        return "";
    }
}
