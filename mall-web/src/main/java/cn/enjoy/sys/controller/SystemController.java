package cn.enjoy.sys.controller;

import cn.enjoy.config.Config;
import cn.enjoy.core.utils.response.HttpResponseBody;
import cn.enjoy.sys.security.MySessionContext;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  注意此类中的方法都可在不登录的情况下调用
 * @author Ray
 * @date 2017/10/16
 */
@RestController
@RequestMapping("/api/sys")
public class SystemController extends BaseController {

    @Resource
    private Config config;

    @GetMapping("config")
    public HttpResponseBody getConfig(){
        return HttpResponseBody.successResponse("ok", config);
    }

    @PostMapping("logout")
    public HttpResponseBody logoutBySessionId(String sid){
        Session session = MySessionContext.getSession(sid);
        if(session != null){
            session.stop();
        }
        return HttpResponseBody.successResponse("ok");
    }


}
