package cn.enjoy.users.service.impl;

import cn.enjoy.users.dao.SysUserRoleMapper;
import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.sys.model.SysUserRole;
import cn.enjoy.sys.service.IUserRoleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService{

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Override
	public List<SysUserRole> queryUserRoleByUserId(String userId) {
		
		return sysUserRoleMapper.queryUserRoleByUserId(userId);
	}



}
