package cn.enjoy.mall.constant;
/**
 * 订单状态(0-待确认，1-已确认，2-已收货，3-已取消，4-已完成、5-已作废)
 */
public enum OrderStatus {
    UNCONFIRMED( 0, "待确认"),
    CONFIRMED( 1, "已确认"),
    RECEIVED( 2, "已收货"),
    CANCELED( 3, "已取消"),
    FINISHED( 4, "已完成"),
    INVALID( 5, "已作废");

    private Integer code;
    private String desc;

    OrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(Integer code){
        for(OrderStatus orderStatus : OrderStatus.values()){
            if(code.intValue() == orderStatus.getCode()){
                return orderStatus.getDesc();
            }
        }
        return "";
    }
}
