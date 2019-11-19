package cn.enjoy.mall.constant;

/**
 * 发货状态（0-未发货、1-已发货、2-部分发货）
 */
public enum ShippingStatus {
    UNSHIPPED( 0, "未发货"),
    SHIPPED( 1, "已发货"),
    PARTIAL_SHIPPED( 2, "部分发货");

    private Integer code;
    private String desc;

    ShippingStatus(Integer code, String desc) {
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
