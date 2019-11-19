package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.mall.model.Brand;
import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.mall.dao.BrandMapper;
import cn.enjoy.mall.service.manage.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class BrandServiceImpl implements IBrandService{
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> getAll() {
        return brandMapper.selectAll();
    }
}
