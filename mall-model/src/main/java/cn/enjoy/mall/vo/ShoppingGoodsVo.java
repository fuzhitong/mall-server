package cn.enjoy.mall.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShoppingGoodsVo implements Serializable{
    private Integer specGoodsId;
    private Integer goodsId;
    private String goodsName;
    private String originalImg;
    private BigDecimal price;
    private Integer num;
    private Integer status;//0：未选中，1：选中
    private String keyName;

    public Integer getSpecGoodsId() {
        return specGoodsId;
    }

    public void setSpecGoodsId(Integer specGoodsId) {
        this.specGoodsId = specGoodsId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
