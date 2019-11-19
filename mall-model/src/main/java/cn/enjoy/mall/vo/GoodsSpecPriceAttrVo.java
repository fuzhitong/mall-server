package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.GoodsAttr;
import cn.enjoy.mall.model.SpecGoodsPrice;

import java.io.Serializable;
import java.util.List;

/**
 * 商品已选规格和属性
 * 【用于后端：用户保存商品已选规格和属性】
 */
public class GoodsSpecPriceAttrVo implements Serializable{
    private Integer goodsId;
    private Short goodsType;
    private List<SpecGoodsPrice> specPriceList;
    private List<GoodsAttr> attrList;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Short getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Short goodsType) {
        this.goodsType = goodsType;
    }

    public List<SpecGoodsPrice> getSpecPriceList() {
        return specPriceList;
    }

    public void setSpecPriceList(List<SpecGoodsPrice> specPriceList) {
        this.specPriceList = specPriceList;
    }

    public List<GoodsAttr> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<GoodsAttr> attrList) {
        this.attrList = attrList;
    }
}
