package cn.enjoy.mall.service;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;

public interface IKillGoodsService {

    KillGoodsSpecPriceDetailVo findOneByKillGoodsId(Integer killGoodsId);

    GridModel<KillGoodsSpecPriceDetailVo> searchList(int page, int pageSize);

    void publishAll2MongoDB();

    void publishGoods2MongoDB(Integer killGoodsId);

}
