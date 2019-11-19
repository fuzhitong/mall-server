package cn.enjoy.mall.service.impl;

import cn.enjoy.mall.mongo.GoodsCategoryDao;
import cn.enjoy.mall.vo.*;
import cn.enjoy.sys.service.ISysParamService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.mall.constant.SysParamCode;
import cn.enjoy.mall.dao.BrandMapper;
import cn.enjoy.mall.dao.GoodsCategoryMapper;
import cn.enjoy.mall.dao.SpecGoodsPriceMapper;
import cn.enjoy.mall.model.Brand;
import cn.enjoy.mall.model.GoodsCategory;
import cn.enjoy.mall.model.SpecGoodsPrice;
import cn.enjoy.mall.mongo.CategoryCountDao;
import cn.enjoy.mall.mongo.GoodsDao;
import cn.enjoy.mall.mongo.HotCategoryDao;
import cn.enjoy.mall.service.IGoodsCategoryService;


import cn.enjoy.sys.model.SysParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ray
 * @date 2018/2/5.
 */
@Service
public class GoodsCategoryServiceImpl implements IGoodsCategoryService{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private GoodsCategoryDao goodsCategoryDao;
    @Resource
    private CategoryCountDao categoryCountDao;
    @Resource
    private HotCategoryDao hotCategoryDao;

    @Resource
    private ISysParamService sysParamService;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private SpecGoodsPriceMapper specGoodsPriceMapper;
    @Value("${goods.pic.domain}")
    private String goodsPicDomain;

    @Override
    public List<CategoryTree> selectCategoryTree(String parentId, String keywords) {
        return goodsCategoryMapper.selectCategoryTree(parentId, keywords);
    }

    @Override
    public List<CategoryTree> selectCategoryTree3(String parentId, String keywords) {
        return goodsCategoryMapper.selectCategoryTree3(parentId, keywords);
    }

    @Override
    public List<CategoryTree> selectCategoryByParentId(Integer parentId) {
        return goodsCategoryMapper.selectCategoryByParentId(parentId, null);
    }

    @Override
    public List<CategoryTree> selectCategory4Home(Integer parentId) {
        List<CategoryTree> list =  goodsCategoryMapper.selectCategoryByParentId(parentId, 1);
        for(CategoryTree categoryTree :list){
            List<CategoryTree> goods = new ArrayList<>();
            //查找所有的子类
            List<GoodsCategory> categories = goodsCategoryMapper.selectSubCategoryByParentId(categoryTree.getId());
            //根据子类来找商品
            outer:
            for(GoodsCategory category:categories){
                List<GoodsVo> subGoodsList = goodsDao.findByCategory(category.getId());
                for(GoodsVo goodsVo:subGoodsList){
                    //取默认的商品规格，因为查商品详情是按规格ID来查的
                    List<SpecGoodsPrice> specGoodsPriceList = goodsVo.getSpecGoodsPriceList();
                    if(specGoodsPriceList != null && specGoodsPriceList.size() > 0){
                        goodsVo.setDefaultSpecId(specGoodsPriceList.get(0).getId());
                        goods.add(this.map2Obj(goodsVo));
                        //一个分类最多显示24个商品
                        if(goods.size() > 23){
                            break outer;
                        }
                    }
                }
            }
            categoryTree.setNodes(goods);

        }


        return list;
    }

    private CategoryTree map2Obj(GoodsVo goodsVo){
        CategoryTree categoryTree = new CategoryTree();
        String goodsName = goodsVo.getBase().getGoodsName();
        categoryTree.setName(goodsName);
        categoryTree.setImage(goodsVo.getBase().getOriginalImg());
        categoryTree.setId(goodsVo.getDefaultSpecId());
        return categoryTree;
    }

    @Override
    public void produceCategory4Home() {
        List<CategoryTree> list = this.selectCategory4Home(0);
        Query query = new Query();
        goodsCategoryDao.remove(query);
        goodsCategoryDao.insert(list);
    }

    @Override
    public List<CategoryTree> getCategory4HomeFromMg(){
        return goodsCategoryDao.find(null, CategoryTree.class);
    }

    @Override
    public void produceCategoryGoodsCount(){
        List<GoodsCategory> categories = goodsCategoryMapper.selectAll();

        List<CategoryCountVo> list = new ArrayList<>();
        for(GoodsCategory goodsCategory:categories){
            //查找所有的子类
            List<GoodsCategory> subCategories = goodsCategoryMapper.selectSubCategoryByParentId(goodsCategory.getId());
            int subGoodsCount = 0;
            for(GoodsCategory subCategory:subCategories) {
                subGoodsCount += goodsDao.countByCategory(subCategory.getId());
            }
            if(subGoodsCount > 0) {
                CategoryCountVo vo = new CategoryCountVo();
                vo.setCatId(goodsCategory.getId());
                vo.setName(goodsCategory.getName());
                vo.setCount(subGoodsCount);
                list.add(vo);
            }
        }
        categoryCountDao.remove(new Query());
        categoryCountDao.insert(list);
    }

    /**
     * 查询热门2级分类
     */
    @Override
    public void produceHotCategories(){
        GoodsCategory param = new GoodsCategory();
        param.setLevel((short) 2);
        param.setIsHot(true);
        param.setIsShow(true);
        List<GoodsCategory> categories = goodsCategoryMapper.selectList(param);
        hotCategoryDao.remove(new Query());
        hotCategoryDao.insert(categories);
    }

    @Override
    public List<CategoryCountVo> searchList(String keyword) {
        List<CategoryCountVo> list = new ArrayList<>();
        if(StringUtils.isEmpty(keyword)){
            SysParam sysParam = sysParamService.selectByCode(SysParamCode.HOME_SEARCH_KEYWORDS);
            if(sysParam != null){
                String[] keywords = sysParam.getValue().split(",");
                for(String searchKey : keywords){
                    List<CategoryCountVo> categories = categoryCountDao.find(new Query(Criteria.where("name").regex(searchKey)), CategoryCountVo.class);
                    list.addAll(categories);
                }
            }
        } else {
            list = categoryCountDao.find(new Query(Criteria.where("name").regex(keyword)).limit(10), CategoryCountVo.class);
            if(list == null){
                list = new ArrayList<>();
            }
            //如果根据分类只找到9个以下，那就根据商品名称来搜索
            if(list.size() < 10){
                int goodsCount = goodsDao.countByName(keyword);
                if(goodsCount > 0) {
                    CategoryCountVo vo = new CategoryCountVo();
                    vo.setName(keyword);
                    vo.setCount(goodsCount);
                    list.add(vo);
                }
            }
        }
        //list去重
        List<CategoryCountVo> newList = new ArrayList<>();
        list.forEach(categoryCountVo -> {
            if(!newList.contains(categoryCountVo)){
                newList.add(categoryCountVo);
            }
        });
        return newList;
    }

    @Override
    public List<GoodsCategory> getClassification() {
        return hotCategoryDao.find(new Query(), GoodsCategory.class);
    }

    @Override
    public List<Brand> getBrands(){
        Brand param = new Brand();
        param.setIsHot(true);
        return brandMapper.selectList(param);
    }

    @Override
    public List<HotGoodsVo> getHotGoods() {
        //查询推荐二级分类TOP5
        List<HotGoodsVo> hotCats = hotCategoryDao.find(new Query().with(new Sort(Sort.Direction.ASC, "sortOrder")).limit(5), HotGoodsVo.class);
        for(HotGoodsVo vo : hotCats){
            //找商品: 属于该分类或其子分类，并且是推荐的商品
           List<GoodsPageVo> list = goodsDao.findList(new Query(new Criteria()
                                                            .and("base.isOnSale").is(true)
                                                            .and("base.isRecommend").is(true)
                                                            .and("base.catId").in(this.getSubCats(vo.getId())))
                                                            .limit(10), GoodsPageVo.class);
            vo.setList(list);
        }
        return hotCats;
    }

    /**
     * 获取子分类(包括自己)
     *
     * @param catId
     * @return
     */
    @Override
    public List<Integer> getSubCats(int catId) {
        List<Integer> allCatIds = new ArrayList<>();
            allCatIds.add(catId);

            //找到分类的子分类
            List<GoodsCategory> subCats = goodsCategoryMapper.selectSubCategoryByParentId(catId);
            if (subCats != null && subCats.size() > 0) {
                for (GoodsCategory subCat : subCats) {
                    allCatIds.add(subCat.getId());
                }
            }
        return allCatIds;
    }

    /**
     * 获取子分类(包括自己)
     *
     * @param catName
     * @return
     */
    @Override
    public List<Integer> getSubCats(String catName) {
        List<Integer> allCatIds = new ArrayList<>();
        List<Integer> catIds = goodsCategoryMapper.selectIdByName(catName);
        if (catIds != null && catIds.size() > 0) {
            allCatIds.addAll(catIds);

            for (Integer catId : catIds) {
                //找到分类的子分类
                List<GoodsCategory> subCats = goodsCategoryMapper.selectSubCategoryByParentId(catId);
                if (subCats != null && subCats.size() > 0) {
                    for (GoodsCategory subCat : subCats) {
                        allCatIds.add(subCat.getId());
                    }
                }
            }
        }
        return allCatIds;
    }
}
