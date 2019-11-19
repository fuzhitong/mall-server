package cn.enjoy.mall.dao;

import cn.enjoy.mall.model.KillGoodsPrice;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;

public interface KillGoodsPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KillGoodsPrice record);

    int selectCountBySpecGoodId(Integer specGoodsId);
    KillGoodsPrice selectByPrimaryKey(Integer id);
    KillGoodsSpecPriceDetailVo detailBySpecGoodId(Integer specGoodsId);
    KillGoodsSpecPriceDetailVo detail(Integer id);

    int updateByPrimaryKey(KillGoodsPrice record);

    PageList<KillGoodsSpecPriceDetailVo> select(@Param("keyword") String keyword, PageBounds pageBounds);
    PageList<KillGoodsSpecPriceDetailVo> selectView(PageBounds pageBounds);
}