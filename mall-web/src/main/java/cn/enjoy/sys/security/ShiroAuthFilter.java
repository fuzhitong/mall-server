package cn.enjoy.sys.security;


import cn.enjoy.sys.service.IResourceService;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.sys.model.SysResource;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public class ShiroAuthFilter extends PassThruAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Reference
    private IResourceService iResourceService;

    /**
     * 这是没权限的路径，不是没登录的
     */
    private String unauthorizedUrl  = "/api/system/accessDenied";
    private Set<String> ignoreSaveRequestUrl;//在xml文件中加载不需要保存的url链接

    public Set<String> getIgnoreSaveRequestUrl() {
        return ignoreSaveRequestUrl;
    }

    public void setIgnoreSaveRequestUrl(Set<String> ignoreSaveRequestUrl) {
        this.ignoreSaveRequestUrl = ignoreSaveRequestUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        //1.判断是否已认证过
        boolean isAuthenticated = subject.isAuthenticated();
        //没有认证并且没有记住登录
        if(!isAuthenticated && !subject.isRemembered()){
            return false;
        }

        boolean isPermitted = true;


        //3.判断用户权限
            List<SysResource> resources = getResourcesFromRquest(request);
        if(resources !=null && resources.size() !=0){
            for(SysResource r : resources){
                //如果为admin用户，可以访问所有菜单
                if("admin".equals(subject.getPrincipal())||subject.getPrincipal().toString().startsWith("admin")){
                    isPermitted =  true;
                }else{
                    isPermitted = subject.isPermitted(r.getCode());

                    //针对几个菜单配置同一个url的情况（对父url做权限控制，而只能查到子url）
                    if(isPermitted) {
                        return true;
                    }
                }
//                if(isPermitted){
//                    //设置面包屑
//                    setCrumbleTitle(request,r);
//                    //设置currentValue
//                    setCurrentValue((ShiroHttpServletRequest)request);
//                    break;
//                }
            }
        }

        return isPermitted;
    }


    /**
     * 根据当前的请求request获取资源列表
     */
    private List<SysResource> getResourcesFromRquest(ServletRequest request) {
        ShiroHttpServletRequest shiroRequet = (ShiroHttpServletRequest)request;
        String requestUri = shiroRequet.getRequestURI();
        if(requestUri.indexOf("/",1) == -1 ){
        	return null;
        }
        String requestPath = requestUri.substring(requestUri.indexOf("/",1));
        return iResourceService.getResourceByUrl(requestPath);
    }




    //处理被阻止的请求
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

    //重写saveRequest,在配置文件中配置的地址不需要保存。实现登录后直接跳转到之前输入的地址。
    @Override
    protected void saveRequest(ServletRequest request) {
        String reqUrl =((ShiroHttpServletRequest)request).getRequestURI();
        if(reqUrl.indexOf("/") > 1) {
            String url = reqUrl.substring(reqUrl.indexOf("/", 1));
            if (!ignoreSaveRequestUrl.contains(url)) {
                WebUtils.saveRequest(request);
            }
        }
    }

   
}

