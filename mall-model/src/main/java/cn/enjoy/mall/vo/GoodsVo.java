package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.*;
import cn.enjoy.mall.model.*;

import java.io.Serializable;
import java.util.List;

/** 注意，此VO保存到mongoDb时要用，不能在图片的get方法里加上域名
 * @author zm
 */
public class GoodsVo implements Serializable {
    private static final long serialVersionUID = -4603735028501129412L;
    private Goods base; //基础信息
    private List<GoodsImages> imageList;//商品相册
    private List<GoodsAttr> attrsList;//商品属性
    private List<SpecGoodsPrice> specGoodsPriceList;//规格价格
    private List<Spec> goodsSpecList;//商品已选的规格列表
    //category parent_id_path,分类的ID路径，便于按分类搜索商品
    private String catIdPath;
    private Integer defaultSpecId;

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

    public List<Spec> getGoodsSpecList() {
        return goodsSpecList;
    }

    public void setGoodsSpecList(List<Spec> goodsSpecList) {
        this.goodsSpecList = goodsSpecList;
    }

    public String getCatIdPath() {
        return catIdPath;
    }

    public void setCatIdPath(String catIdPath) {
        this.catIdPath = catIdPath;
    }

    public Integer getDefaultSpecId() {
        return defaultSpecId;
    }

    public void setDefaultSpecId(Integer defaultSpecId) {
        this.defaultSpecId = defaultSpecId;
    }
}
