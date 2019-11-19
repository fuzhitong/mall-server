package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.core.utils.GridModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import cn.enjoy.mall.dao.GoodsAttributeMapper;
import cn.enjoy.mall.model.GoodsAttribute;
import cn.enjoy.mall.service.manage.IAttributeManageService;

import javax.annotation.Resource;

/**
 * 商品模型
 * @author Ray
 * @date 2018/3/12.
 */
@Service
public class AttributeManageServiceImpl implements IAttributeManageService {

    @Resource
    private GoodsAttributeMapper attributeMapper;
    @Override
    public GridModel<GoodsAttribute> queryByPage(int page, int pageSize, GoodsAttribute attribute) {
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList<GoodsAttribute> list = attributeMapper.queryByPage(attribute, pageBounds);
        return new GridModel<>(list);
    }

    /**
     * 保存规格和规格项
     * @param attribute
     */
    @Override
    public void save(GoodsAttribute attribute) {
        if(attribute.getAttrId() == null){
            //不设1商品编辑看不到
            attribute.setAttrIndex((byte)1);
            attributeMapper.insertSelective(attribute);
        } else {
            attributeMapper.updateByPrimaryKeySelective(attribute);
        }

    }

    /**
     * 更新规格
     * @param spec
     */
    @Override
    public void update(GoodsAttribute spec) {
        attributeMapper.updateByPrimaryKeySelective(spec);
    }

    @Override
    public void delete(int id) {
        attributeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for(String id : ids){
            attributeMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
    }
}
