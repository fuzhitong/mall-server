package cn.enjoy.mall.vo;

import cn.enjoy.mall.SysPropUtil;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsBasePageVo implements Serializable{
    private Integer goodsId;//商品id
    private String goodsName;//商品名称
    private String goodsRemark;//商品描述
    private Short storeCount;//商品库存
    private Short commentCount;//评论数
    private BigDecimal shopPrice;//店面价
    private String originalImg;//商品上传原始图

    private Boolean isRecommend;//是否推荐
    private Boolean isNew;//是否新品
    private Boolean isHot;//是否热卖

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

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public Short getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Short storeCount) {
        this.storeCount = storeCount;
    }

    public Short getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Short commentCount) {
        this.commentCount = commentCount;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getOriginalImg() {
        return SysPropUtil.getPicDomain(originalImg) ;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }
}

