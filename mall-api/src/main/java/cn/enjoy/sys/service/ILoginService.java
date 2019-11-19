package cn.enjoy.sys.service;

import cn.enjoy.sys.model.MenuModel;
import cn.enjoy.sys.model.SysUser;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.List;
import java.util.Map;

public interface ILoginService {
	 Map<String, Object> login(AuthenticationToken token);
	 Map<String, Object> login(String loginAccount, String password);
	 List<MenuModel> queryMenus(String userId, String parentId);
	 List<MenuModel> queryPermissionList(SysUser user);
	 SysUser login(String loginAccount);
}
