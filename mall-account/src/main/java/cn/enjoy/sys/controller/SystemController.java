package cn.enjoy.sys.controller;

import cn.enjoy.config.Config;
import cn.enjoy.core.utils.response.HttpResponseBody;
import cn.enjoy.util.ShiroCacheUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author Ray
 * @date 2017/10/16
 */
@RestController
@RequestMapping("/api/sys")
public class SystemController extends BaseController {

    @Resource
    private Config config;
    @Resource
    private ShiroCacheUtil shiroCacheUtil;

    @GetMapping(value = "/config")
    public HttpResponseBody getConfig(){
        return HttpResponseBody.successResponse("ok", config);
    }


    @RequestMapping(value = "logout", method = {RequestMethod.POST, RequestMethod.GET})
    public HttpResponseBody logout(){
        if(this.getSessionUser() != null) {
            shiroCacheUtil.removeUser(this.getSessionUser().getUserName());
        }
        SecurityUtils.getSubject().logout();
        return HttpResponseBody.successResponse("登出成功");
    }

}
