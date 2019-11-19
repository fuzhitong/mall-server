package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.OrderAction;

/**
 * @author Ray
 * @date 2018/3/20.
 */
public class OrderActionVo extends OrderAction {

    private static final long serialVersionUID = 6388949383115963025L;
    private String orderStatusStr;
    private String userName;

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
