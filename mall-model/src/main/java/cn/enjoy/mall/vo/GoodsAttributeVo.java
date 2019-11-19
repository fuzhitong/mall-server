package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.GoodsAttribute;

/**
 * @author Ray
 * @date 2018/3/15.
 */
public class GoodsAttributeVo extends GoodsAttribute {

    private static final long serialVersionUID = -4030413328198065397L;
    private String goodsType;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
