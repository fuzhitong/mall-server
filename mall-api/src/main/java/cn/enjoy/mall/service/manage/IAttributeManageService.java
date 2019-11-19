package cn.enjoy.mall.service.manage;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.model.GoodsAttribute;

/**
 * @author Ray
 * @date 2018/3/7.
 */
public interface IAttributeManageService {

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param attribute
     * @return
     */
    GridModel<GoodsAttribute> queryByPage(int page, int pageSize, GoodsAttribute attribute);

    /**
     * 保存属性和属性项
     * @param spec Spec
     */
    void save(GoodsAttribute spec);


    /**
     * 更新属性
     * @param spec Spec
     */
    void update(GoodsAttribute spec);

    /**
     * 删除属性
     * @param id
     */
    void delete(int id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(String[] ids);
}
