package cn.enjoy.mall.constant;

/**
 * 支付状态 （0-未支付，1-已支付）
 */
public enum PayStatus {
    UNPAID( 0, "未支付"),
    PAID( 1, "已支付");

    private Integer code;
    private String desc;

    PayStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
