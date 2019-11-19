package cn.enjoy.mall.service.manage;

import cn.enjoy.mall.model.GoodsType;
import cn.enjoy.core.utils.GridModel;

import java.util.List;

/**
 * @author Ray
 * @date 2018/3/7.
 */
public interface IGoodsTypeManageService {

    GridModel<GoodsType> queryByPage(int page, int pageSize, String parentId, String categoryName);

    List<GoodsType> queryAll();

    void save(GoodsType goodsType);

    void delete(short id);

    void deleteByIds(String[] ids);
}
