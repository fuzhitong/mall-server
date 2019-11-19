package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.mall.model.GoodsType;
import cn.enjoy.core.utils.GridModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import cn.enjoy.mall.dao.GoodsTypeMapper;
import cn.enjoy.mall.service.manage.IGoodsTypeManageService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品模型
 * @author Ray
 * @date 2018/3/12.
 */
@Service
public class GoodsTypeManageServiceImpl implements IGoodsTypeManageService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public GridModel<GoodsType> queryByPage(int page, int pageSize, String parentId, String name) {
        PageBounds pageBounds = new PageBounds(page, pageSize);
        return new GridModel<>(goodsTypeMapper.queryByPage(name, pageBounds));
    }

    @Override
    public List<GoodsType> queryAll() {
        return goodsTypeMapper.queryAll();
    }

    @Override
    public void save(GoodsType goodsType) {
        if(goodsType.getId() == null){
            goodsTypeMapper.insert(goodsType);
        } else {
            goodsTypeMapper.updateByPrimaryKeySelective(goodsType);
        }
    }

    @Override
    public void delete(short id) {
        goodsTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for(String id : ids){
            goodsTypeMapper.deleteByPrimaryKey(Short.parseShort(id));
        }
    }
}
