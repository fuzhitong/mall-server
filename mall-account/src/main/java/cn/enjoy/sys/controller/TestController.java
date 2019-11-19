package cn.enjoy.sys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.sys.service.IDictDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ray
 * @date 2018/2/3.
 */
@RestController
public class TestController {

    @Reference
    private IDictDataService dictDataService;

    @GetMapping("test")
    public Object test(String type){
        return dictDataService.findDictByType(type);
    }
}
