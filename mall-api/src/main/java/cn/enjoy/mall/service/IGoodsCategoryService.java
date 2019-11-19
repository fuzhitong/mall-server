package cn.enjoy.mall.service;

import cn.enjoy.mall.model.Brand;
import cn.enjoy.mall.vo.CategoryTree;
import cn.enjoy.mall.vo.HotGoodsVo;
import cn.enjoy.mall.model.GoodsCategory;
import cn.enjoy.mall.vo.CategoryCountVo;

import java.util.List;

/**
 * @author Ray
 * @date 2018/2/5.
 */
public interface IGoodsCategoryService {

    List<CategoryTree> selectCategoryTree(String parentId, String keywords);
    //三层分类树
    List<CategoryTree> selectCategoryTree3(String parentId, String keywords);

    List<CategoryTree> selectCategoryByParentId(Integer parentId);

    List<CategoryTree> selectCategory4Home(Integer parentId);

    void produceCategory4Home();

    List<CategoryTree> getCategory4HomeFromMg();

    /**
     * 自动计算每个分类下的商品数据，存入mongodb
     * @author Ray
     */
    void produceCategoryGoodsCount();

    /**
     * 生成商品查询页的分类数据
     */
    void produceHotCategories();

    /**
     * 搜索框自动提示
     * @param keyword 搜索框的输入
     * @return
     * @author Ray
     */
    List<CategoryCountVo> searchList(String keyword);

    List<GoodsCategory> getClassification();

    List<Brand> getBrands();

    /**
     * 获取5个分类，再每个分类查最多10个商品
     * @return
     */
    List<HotGoodsVo> getHotGoods();

    List<Integer> getSubCats(int catId);

    List<Integer> getSubCats(String catName);

}
