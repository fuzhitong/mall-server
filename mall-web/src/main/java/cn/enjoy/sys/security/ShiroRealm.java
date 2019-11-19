
package cn.enjoy.sys.security;



import cn.enjoy.sys.model.MenuModel;
import cn.enjoy.sys.model.SysRole;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.sys.model.SysUser;
import cn.enjoy.sys.service.ILoginService;
import cn.enjoy.core.utils.CommonConstant;
import cn.enjoy.core.utils.JsonUtils;
import cn.enjoy.core.utils.response.ResponseCodeConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * @Title
 * @Description 获取认证和授权信息
 * @Copyright Copyright (c) 2009</p>
 * @Company 享学信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class ShiroRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    
    @Reference
    private ILoginService iLoginService;

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute(CommonConstant.SESSION_USER_KEY);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加用户角色信息
        List<SysRole> roleList = user.getRoleList();
        if (roleList != null) {
            for (SysRole role : roleList) {
                info.addRole(role.getRoleCode());
            }
        }
        // 添加用户权限信息
        List<MenuModel> permissionList = iLoginService.queryPermissionList(user);
        if (permissionList != null) {
            addMyPermission(info, permissionList);
        }
//        SecurityUtils.getSubject().getSession().setAttribute(CommonConstant.SESSION_USER_PERMISSIONS,info.getStringPermissions());
        return info;
    }
    
    /**
     * 获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
    	UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        SysUser loginUser;
        String userName = token.getUsername();
        String password = new String(token.getPassword());

        if (StringUtils.isEmpty(userName)){
            throw new BusinessException(ResponseCodeConstant.USER_LOGIN_FAIL, "用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new BusinessException(ResponseCodeConstant.USER_LOGIN_FAIL, "密码不能为空");
        }
        loginUser = new SysUser();
        loginUser.setLoginAccount(userName);
        loginUser.setPassword(password);
        try{
            Map<String, Object> user = iLoginService.login(authcToken);
            SysUser sysUser = (SysUser)user.get("user");
            user.remove("user");

            Session session = SecurityUtils.getSubject().getSession();
            setResource(iLoginService, session,sysUser);
            logger.info("登录认证成功*******");
            return new SimpleAuthenticationInfo(token.getPrincipal(), token.getPassword(), token.getUsername());
        }catch(Exception e){
            throw new AuthenticationException("认证出错", e);
        }
    }
    //添加权限方法
    private void addMyPermission(SimpleAuthorizationInfo info, List<MenuModel> permissionList) {
        for (MenuModel menu : permissionList) {
            if (!StringUtils.isEmpty(menu.getCode())) {
                String permission = menu.getCode();
                if (!StringUtils.isEmpty(permission)) {
                    info.addStringPermission(permission);
                }
            }
        }
    }

    public static void setResource(ILoginService loginService, Session session, SysUser user){
        List<MenuModel> menuList = loginService.queryMenus(user.getId(),null);
        session.setAttribute(CommonConstant.SESSION_USER_KEY, user);
        session.setAttribute(CommonConstant.SESSION_USER_NAME_KEY, user.getUserName());
        session.setAttribute(CommonConstant.SESSION_USER_ID_KEY, user.getId());
        session.setAttribute(CommonConstant.SESSION_USER_REAL_NAME_KEY, user.getRealName());
        session.setAttribute(CommonConstant.SESSION_MENUS_KEY, JsonUtils.obj2json(menuList));
    }

}
