package cn.enjoy.mall.web.controller;

import cn.enjoy.mall.model.GoodsType;
import cn.enjoy.sys.controller.BaseController;
import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.service.manage.IGoodsTypeManageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品模型
 * @author Ray
 * @date 2018/3/12.
 */
@RestController
@RequestMapping("/api/goodsType")
public class GoodsTypeController extends BaseController {


    @Reference
    private IGoodsTypeManageService goodsTypeManageService;

    @GetMapping("/queryByPage")
    public HttpResponseBody queryByPage(int page, int rows, String parentId, String name){
        return HttpResponseBody.successResponse("ok", goodsTypeManageService.queryByPage(page, rows, parentId, name));
    }

    @GetMapping("/getAll")
    public HttpResponseBody getAll(){
        return HttpResponseBody.successResponse("ok", goodsTypeManageService.queryAll());
    }


    @PostMapping("/save")
    public HttpResponseBody save(GoodsType goodsType){
        goodsTypeManageService.save(goodsType);
        return HttpResponseBody.successResponse("保存成功");
    }

    @PostMapping("/delete")
    public HttpResponseBody delete(Short id){
        goodsTypeManageService.delete(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("/batchDelete")
    public HttpResponseBody batchDelete(String[] ids){
        if(ids == null || ids.length == 0){
            return HttpResponseBody.failResponse("请选择要删除的数据");
        }
        goodsTypeManageService.deleteByIds(ids);
        return HttpResponseBody.successResponse("批量删除成功");
    }


}
