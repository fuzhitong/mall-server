package cn.enjoy.mall.web.controller;

import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.model.GoodsCategory;
import cn.enjoy.mall.service.IGoodsCategoryService;
import cn.enjoy.mall.service.manage.ICategoryManageService;
import cn.enjoy.sys.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ray
 * @date 2018/3/12.
 */
@RestController
@RequestMapping("/api/goodscategory")
public class GoodsCategoryController extends BaseController {

    @Reference
    private ICategoryManageService categoryManageService;
    @Reference
    private IGoodsCategoryService goodsCategoryService;

    @GetMapping("/queryByPage")
    public HttpResponseBody queryByPage(int page, int rows, String parentId, String categoryName){
        return HttpResponseBody.successResponse("ok", categoryManageService.queryByPage(page, rows, parentId, categoryName));
    }

    @GetMapping("/getCategoryTree")
    public HttpResponseBody getCategoryTree(String parentId, String keywords){
        return HttpResponseBody.successResponse("ok", goodsCategoryService.selectCategoryTree(parentId, keywords));
    }

    @GetMapping("/getCategoryTree3")
    public HttpResponseBody getCategoryTree3(String parentId, String keywords){
        return HttpResponseBody.successResponse("ok", goodsCategoryService.selectCategoryTree3(parentId, keywords));
    }

    @PostMapping("/save")
    public HttpResponseBody save(GoodsCategory goodsCategory){
        categoryManageService.save(goodsCategory);
        return HttpResponseBody.successResponse("保存成功");
    }

    @PostMapping("/delete")
    public HttpResponseBody delete(Short id){
        categoryManageService.delete(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("/batchDelete")
    public HttpResponseBody batchDelete(String[] ids){
        if(ids == null || ids.length == 0){
            return HttpResponseBody.failResponse("请选择要删除的分类");
        }
        categoryManageService.deleteByIds(ids);
        return HttpResponseBody.successResponse("批量删除成功");
    }

    @PostMapping("/refresh")
    public HttpResponseBody refresh(){
        goodsCategoryService.produceHotCategories();
        goodsCategoryService.produceCategory4Home();
        goodsCategoryService.produceCategoryGoodsCount();
        return HttpResponseBody.successResponse("刷新缓存成功");
    }
}
