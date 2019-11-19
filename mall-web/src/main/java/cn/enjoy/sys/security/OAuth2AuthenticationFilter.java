package cn.enjoy.sys.security;

import cn.enjoy.core.utils.CommonConstant;
import cn.enjoy.mall.constant.SsoConstants;
import cn.enjoy.sys.model.SysUser;
import cn.enjoy.util.ShiroCacheUtil;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author fangxiaobai on 2017/10/13 17:17.
 * @description OAuth2 自定义  认证过滤器。
 */
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {
    
    // oauth2 authc code 参数名
    private String authCodeParam = "code";
    
    // 客户端id
    private String clientId;
    
    //服务器端登录成功/失败后重定向到的客户端地址。
    private String redirectUrl;
    
    // oauth2 服务器响应类型
    private String responseType = "code";
    
    private String failureUrl;
    private String unauthorizedUrl  = "/api/system/accessDenied";
    private String userInfoUrl;

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }
    private ShiroCacheUtil shiroCacheUtil;

    public void setShiroCacheUtil(ShiroCacheUtil shiroCacheUtil) {
        this.shiroCacheUtil = shiroCacheUtil;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String code = this.getCodeFromCookie(httpServletRequest);
        return new OAuth2Token(code);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if(!subject.isAuthenticated()) {
            // 从cookie 取code
            if(StringUtils.isEmpty(this.getCodeFromCookie((HttpServletRequest) request))) {
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }


        return executeLogin(request, response);
    }

    private String getCodeFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(authCodeParam)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        SysUser user = (SysUser) session.getAttribute(CommonConstant.SESSION_USER_KEY);
        if(user == null){
            return false;
        }

        //主动检查改为了被动接收SSO的logout通知 （api/sys/logout）
        /*if(StringUtil.isEmpty(shiroCacheUtil.getUser(user.getUserName()))){
            subject.logout();
            return false;
        }*/
        return super.isAccessAllowed(request, response, mappedValue);
    }


    /**
     * 从sso拿用户信息,暂时没用
     * @param session
     * @return
     */
    private String getLoginNameFromSso(Session session){
        String accessToken = (String) session.getAttribute(CommonConstant.SESSION_ACCESS_TOKEN);
        try {
            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl).setAccessToken(accessToken).buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            return resourceResponse.getBody();
        } catch (OAuthSystemException | OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSsoSessionIdFromCookie(ServletRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        for(Cookie cookie:cookies){
            if(SsoConstants.SSO_SESSION_ID.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return "";
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        Subject subject = getSubject(request, response);
        if(subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return false;
}
    
    public String getAuthCodeParam() {
        return authCodeParam;
    }
    
    public void setAuthCodeParam(String authCodeParam) {
        this.authCodeParam = authCodeParam;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getRedirectUrl() {
        return redirectUrl;
    }
    
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    
    public String getResponseType() {
        return responseType;
    }
    
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    
    public String getFailureUrl() {
        return failureUrl;
    }
    
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }
}
