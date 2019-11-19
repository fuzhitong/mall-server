package cn.enjoy.sys.controller;

import cn.enjoy.sys.model.SysUser;
import cn.enjoy.core.utils.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Ray on 2017/10/17.
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public String getSessionUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(CommonConstant.SESSION_USER_NAME_KEY);
    }

    public String getSessionUserRealName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(CommonConstant.SESSION_USER_REAL_NAME_KEY);
    }

    public String getSessionUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(CommonConstant.SESSION_USER_ID_KEY);
    }

    public String getSessionUserId() {
        return this.getSessionUserId(this.getRequest());
    }

    public SysUser getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (SysUser) session.getAttribute(CommonConstant.SESSION_USER_KEY);
    }

    public SysUser getSessionUser() {
        return this.getSessionUser(this.getRequest());
    }

    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    public String getSessionUrl(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        sb.append(":");
        sb.append(request.getServerPort());
        sb.append(request.getContextPath());
        return sb.toString();
    }
}

