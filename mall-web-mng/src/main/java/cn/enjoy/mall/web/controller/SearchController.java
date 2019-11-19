package cn.enjoy.mall.web.controller;

import cn.enjoy.mall.service.IGoodsCategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ray
 * @date 2018/2/11.
 */
@RestController
@RequestMapping("api/search")
public class SearchController {

    @Reference
    private IGoodsCategoryService goodsCategoryService;

    @GetMapping("getClassification")
    public HttpResponseBody getClafication(){
        Map<String, Object> data = new HashMap<>();
        //分类
        data.put("types", goodsCategoryService.getClassification());
        //品牌
        data.put("brands", goodsCategoryService.getBrands());
        return HttpResponseBody.successResponse("ok", data);
    }
}
