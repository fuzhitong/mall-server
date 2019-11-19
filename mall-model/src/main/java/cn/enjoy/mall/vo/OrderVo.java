package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.Order;

/**
 * @author Ray
 * @date 2018/3/19.
 */
public class OrderVo extends Order{
    private static final long serialVersionUID = 4281411716839679094L;

    private String fromTime;
    private String toTime;
    private String orderStatusStr;
    private String shippingStatusStr;
    private String userName;

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public String getShippingStatusStr() {
        return shippingStatusStr;
    }

    public void setShippingStatusStr(String shippingStatusStr) {
        this.shippingStatusStr = shippingStatusStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
