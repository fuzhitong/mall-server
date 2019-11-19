package cn.enjoy.mall.vo;

import cn.enjoy.mall.model.SpecGoodsPrice;


import java.io.Serializable;
import java.util.List;

public class GoodsPageVo implements Serializable {
    private GoodsBasePageVo base; //基础信息
    private List<SpecGoodsPrice> specGoodsPriceList;//规格价格

    public GoodsBasePageVo getBase() {
        return base;
    }

    public void setBase(GoodsBasePageVo base) {
        this.base = base;
    }

    public List<SpecGoodsPrice> getSpecGoodsPriceList() {
        return specGoodsPriceList;
    }

    public void setSpecGoodsPriceList(List<SpecGoodsPrice> specGoodsPriceList) {
        this.specGoodsPriceList = specGoodsPriceList;
    }
}
