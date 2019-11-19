package cn.enjoy.mall.service.manage;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.model.OrderGoods;
import cn.enjoy.mall.vo.OrderVo;

import java.util.List;

/**
 * @author Ray
 * @date 2018/3/7.
 */
public interface IOrderManageService {

    GridModel<OrderVo> queryByPage(int page, int pageSize, OrderVo params);

    OrderVo queryOrderDetail(int orderId);

    void save(OrderVo orderVo);

    void delete(short id);

    void deleteByIds(String[] ids);

    List<OrderGoods> selectGoodsByOrderId(Integer orderId);

    int update(Order order);

}
