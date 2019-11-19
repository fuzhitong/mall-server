package cn.enjoy.sys.service;

import cn.enjoy.sys.model.SysUserRole;

import java.util.List;

public interface IUserRoleService {
	List<SysUserRole> queryUserRoleByUserId(String userId);
}
