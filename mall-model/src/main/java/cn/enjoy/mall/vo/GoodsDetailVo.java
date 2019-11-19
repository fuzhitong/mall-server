package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.*;


import java.io.Serializable;
import java.util.List;

/**
 * 后台订单编辑时用
 */
public class GoodsDetailVo implements Serializable{
    private Goods base; //基础信息
    private List<GoodsImages> imageList;//商品相册
    private List<GoodsAttr> attrsList;//商品属性
    private List<SpecGoodsPrice> specGoodsPriceList;//规格价格
    private List<GoodsAttribute> typeGoodsAttributeList;//商品模型对应的属性，属性值用商品已选的属性值覆盖
    private List<Spec> typeSpecList;//商品模型对应的规格，规格项增加checked字段，用于标记上已被商品选择

    public Goods getBase() {
        return base;
    }

    public void setBase(Goods base) {
        this.base = base;
    }

    public List<GoodsImages> getImageList() {
        return imageList;
    }

    public void setImageList(List<GoodsImages> imageList) {
        this.imageList = imageList;
    }

    public List<GoodsAttr> getAttrsList() {
        return attrsList;
    }

    public void setAttrsList(List<GoodsAttr> attrsList) {
        this.attrsList = attrsList;
    }

    public List<SpecGoodsPrice> getSpecGoodsPriceList() {
        return specGoodsPriceList;
    }

    public void setSpecGoodsPriceList(List<SpecGoodsPrice> specGoodsPriceList) {
        this.specGoodsPriceList = specGoodsPriceList;
    }

    public List<GoodsAttribute> getTypeGoodsAttributeList() {
        return typeGoodsAttributeList;
    }

    public void setTypeGoodsAttributeList(List<GoodsAttribute> typeGoodsAttributeList) {
        this.typeGoodsAttributeList = typeGoodsAttributeList;
    }

    public List<Spec> getTypeSpecList() {
        return typeSpecList;
    }

    public void setTypeSpecList(List<Spec> typeSpecList) {
        this.typeSpecList = typeSpecList;
    }
}
