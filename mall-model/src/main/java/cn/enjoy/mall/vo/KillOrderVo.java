package cn.enjoy.mall.vo;

import java.io.Serializable;

public class KillOrderVo implements Serializable{
    private static final long serialVersionUID = 1L;

    private KillGoodsSpecPriceDetailVo killGoodsSpecPriceDetailVo;
    private int addressId;
    private String userId;

    public KillGoodsSpecPriceDetailVo getKillGoodsSpecPriceDetailVo() {
        return killGoodsSpecPriceDetailVo;
    }

    public void setKillGoodsSpecPriceDetailVo(KillGoodsSpecPriceDetailVo killGoodsSpecPriceDetailVo) {
        this.killGoodsSpecPriceDetailVo = killGoodsSpecPriceDetailVo;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
