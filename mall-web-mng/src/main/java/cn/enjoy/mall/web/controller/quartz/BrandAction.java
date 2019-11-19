package cn.enjoy.mall.web.controller.quartz;

import cn.enjoy.sys.controller.BaseController;
import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.service.manage.IBrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brand")
public class BrandAction extends BaseController {
    @Reference
    private IBrandService brandService;

    @GetMapping("/getAll")
    public HttpResponseBody getAll(){
        return HttpResponseBody.successResponse("ok",brandService.getAll());
    }

}
