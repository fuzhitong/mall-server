package cn.enjoy.mall.vo;

import java.io.Serializable;
import java.util.List;

public class OrderCreateVo implements Serializable{
    private Integer addressId;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
