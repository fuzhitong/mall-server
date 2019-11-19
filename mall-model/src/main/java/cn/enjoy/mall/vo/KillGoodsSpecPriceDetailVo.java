package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.KillGoodsPrice;

import java.io.Serializable;

public class KillGoodsSpecPriceDetailVo extends KillGoodsPrice implements Serializable{
    private static final long serialVersionUID = 1L;

    private String goodsName;
    private String originalImg;
    private String key;
    private String keyName;


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
