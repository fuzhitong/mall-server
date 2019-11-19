package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.GoodsAttribute;
import cn.enjoy.mall.model.Spec;

import java.io.Serializable;
import java.util.List;

/**
 * 商品所有规格和所有属性（已选规格项标记了checked=true，已选属性使用已设置的值）
 * 【用于后端：商品编辑页面，根据goodsId和typeId获取的商品所有规格和所有属性】
 */
public class GoodsSpecAttrVo implements Serializable{
    private Integer goodsId;
    private List<Spec> specList;
    private List<GoodsAttribute> attributeList;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public List<GoodsAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<GoodsAttribute> attributeList) {
        this.attributeList = attributeList;
    }
}
