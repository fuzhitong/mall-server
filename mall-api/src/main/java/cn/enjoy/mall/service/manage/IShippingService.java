package cn.enjoy.mall.service.manage;

import cn.enjoy.mall.model.Shipping;

import java.util.List;

/**
 * @author Ray
 * @date 2018/3/21.
 */
public interface IShippingService {

    /**
     * 查询物流公司
     * @return
     */
    List<Shipping> queryAll();

}
