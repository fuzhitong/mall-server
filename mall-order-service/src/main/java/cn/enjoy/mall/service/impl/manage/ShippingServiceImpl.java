package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.mall.dao.ShippingMapper;
import cn.enjoy.mall.model.Shipping;
import cn.enjoy.mall.service.manage.IShippingService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ray
 * @date 2018/3/21.
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    @Override
    public List<Shipping> queryAll() {
        return shippingMapper.queryAll();
    }


}
