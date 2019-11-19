package cn.enjoy.mall.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class HotSellingGoods implements Serializable{
    private Integer goodsId;
    private Integer specGoodsId;
    private String goodsName;
    private String originalImg;
    private BigDecimal marketPrice;
    private BigDecimal shopPrice;
    private Long salesSum;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSpecGoodsId() {
        return specGoodsId;
    }

    public void setSpecGoodsId(Integer specGoodsId) {
        this.specGoodsId = specGoodsId;
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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Long getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(Long salesSum) {
        this.salesSum = salesSum;
    }
}
