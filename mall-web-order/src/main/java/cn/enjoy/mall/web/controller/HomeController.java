package cn.enjoy.mall.web.controller;

import cn.enjoy.mall.service.IGoodsCategoryService;
import cn.enjoy.sys.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.core.utils.JsonUtils;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Ray
 * @date 2018/2/5.
 */
@RestController
@RequestMapping("/api/home")
public class HomeController extends BaseController {

    @Reference//(url = "dubbo://127.0.0.1:20880")
    private IGoodsCategoryService goodsCategoryService;


    /**
     * 首页获取分类
     * @return HttpResponseBody
     */
    @GetMapping("getAllGroupItems")
    public HttpResponseBody getAllGroupItems(){
        return HttpResponseBody.successResponse("ok", goodsCategoryService.getCategory4HomeFromMg());
    }

    /**
     * 首页轮播图
     * @return HttpResponseBody
     */
    @GetMapping("getSliderList")
    public HttpResponseBody getSliderList(){
        String json = "[" +
                "    {" +
                "      \"image\": \"//i1.mifile.cn/a4/xmad_15169761220549_oNiXf.jpg\"," +
                "      \"id\": \"https://item.mi.com/product/10000080.html\"" +
                "    }," +
                "    {" +
                "      \"image\": \"//i1.mifile.cn/a4/xmad_15172318170126_ysQaw.jpg\"," +
                "      \"id\": \"https://item.mi.com/product/10000069.html\"" +
                "    }," +
                "    {" +
                "      \"image\": \"//i1.mifile.cn/a4/xmad_1517313508995_XRANt.jpg\"," +
                "      \"id\": \"https://item.mi.com/product/10000080.html\"" +
                "    }," +
                "    {" +
                "      \"image\": \"//i1.mifile.cn/a4/xmad_15169762822641_YeJHE.jpg\"," +
                "      \"id\": \"https://item.mi.com/product/7440.html\"" +
                "    }," +
                "    {" +
                "      \"image\": \"//i1.mifile.cn/a4/xmad_15173096638829_ORSVB.jpg\"," +
                "      \"id\": \"https://item.mi.com/product/10000080.html\"" +
                "    }" +
                "  ]";
        return HttpResponseBody.successResponse("ok", JsonUtils.json2ArrayList(json, Map.class));
    }


    /**
     * 首页查询框下拉提示
     * @return HttpResponseBody
     */
    @GetMapping("getSearchList")
    public HttpResponseBody getSearchList(String param){
        return HttpResponseBody.successResponse("ok", goodsCategoryService.searchList(param));
    }

    /**
     * 首页大分类商品
     * @return HttpResponseBody
     */
    @GetMapping("getHotGoods")
    public HttpResponseBody getHotGoods(){
        return HttpResponseBody.successResponse("ok", goodsCategoryService.getHotGoods());
    }

}
