package cn.enjoy.mall.service.manage;

import cn.enjoy.mall.model.GoodsCategory;
import cn.enjoy.core.utils.GridModel;

/**
 * @author Ray
 * @date 2018/3/7.
 */
public interface ICategoryManageService {

    GridModel<GoodsCategory> queryByPage(int page, int pageSize, String parentId, String categoryName);

    void save(GoodsCategory goodsCategory);

    void delete(short id);

    void deleteByIds(String[] ids);
}
