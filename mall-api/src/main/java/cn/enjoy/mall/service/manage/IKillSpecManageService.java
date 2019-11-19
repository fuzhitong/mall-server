package cn.enjoy.mall.service.manage;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.model.KillGoodsPrice;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;

public interface IKillSpecManageService {
    int delete(Integer id);

    int save(KillGoodsPrice record);

    int selectCountBySpecGoodId(Integer specGoodsId);

    KillGoodsPrice selectByPrimaryKey(Integer id);
    KillGoodsSpecPriceDetailVo detailBySpecGoodId(Integer id);
    KillGoodsSpecPriceDetailVo detailById(Integer id);

    int update(KillGoodsPrice record);
    void flushCache(KillGoodsPrice record);

    GridModel<KillGoodsSpecPriceDetailVo> queryByPage(String name,int page, int pageSize);
    GridModel<KillGoodsSpecPriceDetailVo> queryView(int page, int pageSize);

}
