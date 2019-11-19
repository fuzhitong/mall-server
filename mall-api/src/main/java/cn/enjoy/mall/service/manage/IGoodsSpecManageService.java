package cn.enjoy.mall.service.manage;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.model.Spec;

/**
 * @author Ray
 * @date 2018/3/7.
 */
public interface IGoodsSpecManageService {

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    GridModel<Spec> queryByPage(int page, int pageSize, String name, Integer typeId);

    /**
     * 保存规格和规格项
     * @param spec Spec
     */
    void save(Spec spec);


    /**
     * 更新规格
     * @param spec Spec
     */
    void update(Spec spec);

    /**
     * 删除规格
     * @param id
     */
    void delete(int id);

    /**
     * 批量删除规格
     * @param ids
     */
    void deleteByIds(String[] ids);
}
