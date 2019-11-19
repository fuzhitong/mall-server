package cn.enjoy.mall.dao;

import cn.enjoy.mall.vo.OrderVo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


/**
 * @author Ray
 * @date 2018/3/19.
 */
public interface OrderManageMapper {

    PageList<OrderVo> queryByPage(OrderVo params, PageBounds pageBounds);
}
