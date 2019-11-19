package cn.enjoy.mall.service;

import cn.enjoy.mall.model.*;
import cn.enjoy.mall.vo.GoodsDetailVo;
import cn.enjoy.mall.vo.GoodsPageVo;
import cn.enjoy.mall.vo.GoodsSpecPriceAttrVo;
import cn.enjoy.mall.vo.GoodsVo;
import cn.enjoy.core.utils.GridModel;

import java.util.List;

/**
 * @author Ray
 * @date 2018/2/5.
 */
public interface IGoodsService {
    /**
     * 从mongodb中查找完整信息
     * @param goodsId
     * @return
     */
    GoodsVo findOneById(Integer goodsId) ;

    /**
     * 从mongodb中查找完整信息
     * @param specGoodsId
     * @return
     */
    GoodsVo findOneBySpecGoodsId(Integer specGoodsId) ;
    GoodsVo findOneByKillGoodsId(Integer killGoodsId);

    /**
     * 从mongodb中分页查找
     * @param catId
     * @param brandId
     * @param order
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    GridModel<GoodsPageVo> searchList(Integer catId, Integer brandId, String order, String keyword, int page, int pageSize);

    /**
     * 保存到数据库
     * @param goods
     * @return
     */
    int save(Goods goods);

    /**
     * 从数据库根据id删除
     * @param goodsId
     * @return
     */
    int delete(Integer goodsId);

    /**
     * 从数据库根据id批量删除
     * @param goodsIds
     * @return
     */
    int deteteBatch(Integer[] goodsIds);

    /**
     * 从数据库分页查询列表
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    GridModel<Goods> queryListPageFromDB(Integer catId, String keyword, int page, int pageSize,String sidx,String sord);

    /**
     * 从数据库查询商品详细信息
     * @param goodsId
     * @return
     */
    GoodsDetailVo queryDetailFromDB(Integer goodsId);

    /**
     * 从数据库查询商品信息
     * @param goodsId
     * @return
     */
    Goods queryGoodsFromDB(Integer goodsId);

    /**
     * 从数据库查询商品图片
     * @param goodsId
     * @return
     */
    List<GoodsImages> queryGoodsImageFromDB(Integer goodsId);

    /**
     * 从数据库查询商品属性信息
     * @param goodsId
     * @return
     */
    List<GoodsAttr> queryGoodsAttrListFromDB(Integer goodsId);
    /**
     * 从数据库根据typeId查询属性信息
     * @param typeId
     * @return
     */
    List<GoodsAttribute> queryGoodsAttributeListByTypeFromDB(Short typeId);

    /**
     * 根据type查询所有的属性，然后根据goodsId显示商品已选的属性值
     * @param goodsId
     * @param typeId
     * @return
     */
    List<GoodsAttribute> queryGoodsAttributeListByGoodsIdAndTypeFromDB(Integer goodsId,Short typeId);

    /**
     * 从数据库查询商品规格信息
     * @param goodsId
     * @return
     */
    List<SpecGoodsPrice> querySpecGoodsPriceListFromDB(Integer goodsId);

    /**
     * 从数据库根据typeId查询规格信息
     * @param typeId
     * @return
     */
    List<Spec> querySpecListByTypeFromDB(Short typeId) ;

    /**
     *  从数据库根据typeId查询所有规格信息，然后根据goodsId标上哪些规格项已选中
     * @param goodsId
     * @param typeId
     * @return
     */
    List<Spec> querySpecListByGoodsIdAndTypeIdFromDB(Integer goodsId,Short typeId);

    /**
     * 保存商品图片到数据库
     * @param imageUrls
     * @param goodsId
     * @return
     */
    int saveGoodsImages2DB(String[] imageUrls, Integer goodsId);

    /**
     * 保存商品规格价格和属性到数据库
     * @param specPriceAttrVo
     */
    void saveGoodsSpecAndAttr2DB(GoodsSpecPriceAttrVo specPriceAttrVo);

    /**
     * 发布所有商品到mongodb
     */
    void publishAll2MongoDB();

    /**
     * 发布单个商品到mongodb
     * @param goodsId
     */
    void publishGoods2MongoDB(Integer goodsId);

    /**
     * 查询热销商品
     * @param showNum
     * @return
     */
    List<HotSellingGoods> queryHotSellingGoods(Integer showNum);
}
