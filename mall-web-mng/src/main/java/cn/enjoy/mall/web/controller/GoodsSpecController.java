package cn.enjoy.mall.web.controller;

import cn.enjoy.mall.model.Spec;
import cn.enjoy.mall.service.manage.IGoodsSpecManageService;
import cn.enjoy.sys.controller.BaseController;
import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * 商品模型
 * @author Ray
 * @date 2018/3/12.
 */
@RestController
@RequestMapping("/api/goodsSpec")
public class GoodsSpecController extends BaseController {


    @Reference
    private IGoodsSpecManageService goodsSpecManageService;

    @GetMapping("/queryByPage")
    public HttpResponseBody queryByPage(int page, int rows, String name, Integer typeId){
        return HttpResponseBody.successResponse("ok", goodsSpecManageService.queryByPage(page, rows, name, typeId));
    }

    /**
     * 保存spec和specItem
     * @param spec
     * @return
     */
    @PostMapping("/save")
    public HttpResponseBody save(@RequestBody Spec spec){
        goodsSpecManageService.save(spec);
        return HttpResponseBody.successResponse("保存成功");
    }

    /**
     * 只更新spec的 searchIndex和order
     * @param spec
     * @return
     */
    @PostMapping("/update")
    public HttpResponseBody update(@RequestBody Spec spec){
        goodsSpecManageService.update(spec);
        return HttpResponseBody.successResponse("保存成功");
    }



    @PostMapping("/delete")
    public HttpResponseBody delete(Short id){
        goodsSpecManageService.delete(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("/batchDelete")
    public HttpResponseBody batchDelete(String[] ids){
        if(ids == null || ids.length == 0){
            return HttpResponseBody.failResponse("请选择要删除的数据");
        }
        goodsSpecManageService.deleteByIds(ids);
        return HttpResponseBody.successResponse("批量删除成功");
    }


}
