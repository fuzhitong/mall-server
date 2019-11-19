package cn.enjoy.mall.web.controller;

import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.model.GoodsAttribute;
import cn.enjoy.mall.service.manage.IAttributeManageService;
import cn.enjoy.sys.controller.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * 商品可选属性
 * @author Ray
 * @date 2018/3/15.
 */
@RestController
@RequestMapping("/api/goodsAttr")
public class GoodsAttributeController extends BaseController {


    @Reference
    private IAttributeManageService attributeManageService;

    @GetMapping("/queryByPage")
    public HttpResponseBody queryByPage(int page, int rows, GoodsAttribute attribute){
        return HttpResponseBody.successResponse("ok", attributeManageService.queryByPage(page, rows, attribute));
    }

    /**
     * 保存
     * @param attribute
     * @return
     */
    @PostMapping("/save")
    public HttpResponseBody save(@RequestBody GoodsAttribute attribute){
        attributeManageService.save(attribute);
        return HttpResponseBody.successResponse("保存成功");
    }

    /**
     * 更新
     * @param attribute
     * @return
     */
    @PostMapping("/update")
    public HttpResponseBody update(@RequestBody GoodsAttribute attribute){
        attributeManageService.update(attribute);
        return HttpResponseBody.successResponse("保存成功");
    }



    @PostMapping("/delete")
    public HttpResponseBody delete(Short id){
        attributeManageService.delete(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("/batchDelete")
    public HttpResponseBody batchDelete(String[] ids){
        if(ids == null || ids.length == 0){
            return HttpResponseBody.failResponse("请选择要删除的数据");
        }
        attributeManageService.deleteByIds(ids);
        return HttpResponseBody.successResponse("批量删除成功");
    }


}
