package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.mall.dao.GoodsCategoryMapper;
import cn.enjoy.core.utils.GridModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import cn.enjoy.mall.model.GoodsCategory;
import cn.enjoy.mall.service.manage.ICategoryManageService;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2018/3/12.
 */
@Service
public class CategoryManageServiceImpl implements ICategoryManageService {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public GridModel<GoodsCategory> queryByPage(int page, int pageSize, String parentId, String categoryName) {
        PageBounds pageBounds = new PageBounds(page, pageSize, Order.formString("sort_order.asc"));
        return new GridModel<>(goodsCategoryMapper.queryByPage(parentId, categoryName, pageBounds));
    }

    @Override
    public void save(GoodsCategory goodsCategory) {
        if(goodsCategory.getId() == null){
            goodsCategoryMapper.insert(goodsCategory);
            //更新parent_id_path,因为这个值是根据本分类ID来生成的
            goodsCategory.setParentIdPath(goodsCategory.getParentIdPath() + "_" + goodsCategory.getId());
        }
        goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
    }

    @Override
    public void delete(short id) {
        goodsCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for(String id : ids){
            goodsCategoryMapper.deleteByPrimaryKey(Short.parseShort(id));
        }
    }
}
