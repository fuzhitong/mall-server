/**
 * Created on 2016年6月15日 by 周礼
 */
package cn.enjoy.users.service.impl;


import cn.enjoy.users.dao.SysUserMapper;
import cn.enjoy.users.dao.SysUserRoleMapper;
import cn.enjoy.users.dao.UserDepartmentInfoMapper;
import cn.enjoy.core.constant.Constants;
import cn.enjoy.core.constant.SysRoleEnum;
import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.*;
import cn.enjoy.core.utils.response.ResponseCodeConstant;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import cn.enjoy.sys.model.SysUser;
import cn.enjoy.sys.model.SysUserRole;
import cn.enjoy.sys.model.UserDepartmentInfo;
import cn.enjoy.sys.service.IUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2009</p>
 * @Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author 周礼
 * @version 1.0
 * @修改记录：
 * @修改序号，修改日期，修改人，修改内容
 */
@Service
public class UserServiceImpl implements IUserService{
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private UserDepartmentInfoMapper userDepartmentInfoMapper;



    @Override
    @Transactional
    public void addUser(SysUser record, String sessionUserId,  List<String> departIds, List<String> roleIds) {
    	if(StringUtils.isEmpty(record.getId())){
    	    //新增用户校验邮箱是否已被注册
    	    int count = sysUserMapper.checkMobileAndEmail(CommonConstant.USER_STATUS_YES_ACTIVATION, record.getEmail(), null,null,null);
            int count2 = sysUserMapper.checkMobileAndEmail(CommonConstant.USER_STATUS_YES_ACTIVATION, null, record.getMobile(),null,null);
    	    if(count > 0){
    	        throw new BusinessException("此邮箱地址已经被注册");
            }
            if(count2 > 0){
                throw new BusinessException("此手机号码已经被注册");
            }
    		 record.setId(UUIDGenerator.getUUID());

    	}
        record.setStatus(CommonConstant.USER_STATUS_YES_ACTIVATION);//置为已激活
        //record.setIdentityCode(CommonConstant.ACCOUNT_TYPE_COMMON_USER);
        record.setSysCode(Constants.BACKGROUNDCODE);
        String code = RandomUtils.emailActiveValiDateCode(record.getEmail());
        record.setValidateCode(code);
        record.setActivatedTime(new Date());
        record.setCreateTime(new Date());
        //record.setDepartId(departId);
        record.setUpdateTime(new Date());
        String password = record.getPassword();//初始密码
        String passwordRand = CommonConstant.INIT_PASSWORD_RAND;//处理秘钥因子
    	String md5Password = MD5Util.generateMD5( password,passwordRand);
        record.setPassword(md5Password);
        record.setPasswordRand(passwordRand);
        record.setRoleIds(roleIds);
        List<SysUserRole> roleList = new ArrayList<SysUserRole>();
        if(record.getRoleIds() != null && record.getRoleIds().size() !=0){
            for(String roleId : record.getRoleIds()){
                SysUserRole userRole = new SysUserRole();
                userRole.setId(UUIDGenerator.getUUID());
                userRole.setRoleId(roleId);
                userRole.setUserId(record.getId());
                userRole.setCreateTime(new Date());
                userRole.setCreateUser(sessionUserId);
                roleList.add(userRole);
            }
        }
        sysUserMapper.insertSelective(record);
        if(roleList.size() != 0){
        	 sysUserRoleMapper.insertUserRoleByRoleId(roleList);
        }

        saveUserDeparts(record.getId(), sessionUserId, departIds);

        //this.saveEmployee((UserVo) record);
    }


    public void saveUserRole(SysUser record, String sessionUserId, List<String> roleIds){
        List<SysUserRole> roleList = new ArrayList<SysUserRole>();
        if(record.getRoleIds() != null && roleIds.size() !=0){
            for(String roleId :roleIds){
                SysUserRole userRole = new SysUserRole();
                userRole.setId(UUIDGenerator.getUUID());
                userRole.setRoleId(roleId);
                userRole.setUserId(record.getId());
                userRole.setCreateTime(new Date());
                userRole.setCreateUser(sessionUserId);
                roleList.add(userRole);
            }
            if(roleList.size() != 0){
                sysUserRoleMapper.insertUserRoleByRoleId(roleList);
            }
        }
    }

    private void saveUserDeparts(String UserId, String sessionUserId, List<String> departIds) {
        List<UserDepartmentInfo> udis = new ArrayList<>();
        if(!CollectionUtils.isEmpty(departIds)) {
           for(String dpartId:departIds) {
               UserDepartmentInfo udi = new  UserDepartmentInfo();
               udi.setCreateTime(new Date());
               udi.setCreateUser(sessionUserId);
               udi.setId(UUIDGenerator.getUUID());
               udi.setUserId(UserId);
               udi.setDepartmentId(dpartId);
               udis.add(udi);
           }
            userDepartmentInfoMapper.insertUserDepartInfo(udis);
        }
    }

    @Override
    public SysUser selectByPrimaryKey(String userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }



    @Override
    public SysUser selectByEmail(String email){
        return  sysUserMapper.selectByEmail(email);
    }



    @Override
    @Transactional
    public void saveUserRole(String roleId,List<Map<String,String>> map,String crateUserId,String departId){
        Map<String,Object> dm = new HashMap<String,Object>();
        dm.put("roleId", roleId);
        dm.put("departId",departId);
        sysUserRoleMapper.deleteUserRoleByUserIds(dm);
        List<SysUserRole> userRoles = new ArrayList<SysUserRole>();
        if(map != null && map.size() !=0){
            for(int i=0;i<map.size();i++){
                SysUserRole userRole = new SysUserRole();
                Map<String,String> m = map.get(i);
                userRole.setId(UUIDGenerator.getUUID());
                userRole.setRoleId(roleId);
                userRole.setUserId(m.get("userId"));
                userRole.setCreateTime(new Date());
                userRole.setCreateUser(crateUserId);
                userRoles.add(userRole);
            }
            sysUserRoleMapper.insertUserRoleByRoleId(userRoles);
        }

    }
	@Override
	public boolean checkUserPassword(String userId, String password) {
		SysUser user = sysUserMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		String md5Password = MD5Util.generateMD5(password,
				user.getPasswordRand());
		if (!user.getPassword().equals(md5Password)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void modifyUserPassWord(String userId, String oldPassword,
			String newPassword, String repeatPassword) {
		if (!newPassword.equals(repeatPassword)) {
			throw new BusinessException(
					ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL,
					ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL_UNLIKE_MSG);
		}
		if (!checkUserPassword(userId, oldPassword)) {
			throw new BusinessException(
					ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL,
					ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL_NOTPASS_MSG);
		}
		String passwordRandom = RandomUtils.generateNumberString(8);
		String md5Password = MD5Util.generateMD5(newPassword, passwordRandom);
		// 临时对象,避免再写单独的密码更新语句
		SysUser tempUser = new SysUser();
		tempUser.setId(userId);
		tempUser.setPassword(md5Password);
		tempUser.setPasswordRand(passwordRandom);
		sysUserMapper.updateByPrimaryKeySelective(tempUser);
	}

    /***
     * <p><b>description:</b>忘记密码修改</p>
     * @param
     *        userId 用户ID
     *        newPassword    新密码
     *        repeatPassword 确认密码
     */
    @Override
    public void modifyUserForgetThePassword(String userId, String newPassword, String repeatPassword){
        if (!newPassword.equals(repeatPassword)) {
            throw new BusinessException(
                    ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL,
                    ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL_UNLIKE_MSG);
        }
        String passwordRandom = RandomUtils.generateNumberString(8);
        String md5Password = MD5Util.generateMD5(newPassword, passwordRandom);
        // 临时对象,避免再写单独的密码更新语句
        SysUser tempUser = new SysUser();
        tempUser.setId(userId);
        tempUser.setPassword(md5Password);
        tempUser.setPasswordRand(passwordRandom);
        sysUserMapper.updateByPrimaryKeySelective(tempUser);
    }

	@Override
	public void modifyUserMobile(String userId, String mobile) {
		SysUser user = sysUserMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		user.setMobile(mobile);
		sysUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void modifyUserEmail(String userId, String email) {
		SysUser user = sysUserMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		user.setEmail(email);
		sysUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public boolean checkUniqueness(String accountCode) throws BusinessException {
		// 传入的查询条件非空校验
		if (StringUtils.isEmpty(accountCode)) {
			return true;
		}
		// 已作废的用户不在检验范围之内
		if (sysUserMapper.checkUniqueness(
				String.valueOf(CommonConstant.USER_STATUS_CANCEL), accountCode) >= 1) {
			return false;
		} else {
			return true;
		}
	}
    @Override
    @Transactional
    public void editUser(SysUser user, String sessionUserId,String companyId){
        if (!StringUtils.isEmpty(user.getId())){
            if(user.getRoleIds() != null && user.getRoleIds().size() !=0){
                sysUserRoleMapper.deleteUserRoleByUserId(user.getId());
                List<SysUserRole> roleList = new ArrayList<SysUserRole>();
                for(String roleId : user.getRoleIds()){
                    SysUserRole userRole = new SysUserRole();
                    userRole.setId(UUIDGenerator.getUUID());
                    userRole.setRoleId(roleId);
                    userRole.setUserId(user.getId());
                    userRole.setCreateTime(new Date());
                    userRole.setCreateUser(sessionUserId);
                    roleList.add(userRole);
                }
                sysUserRoleMapper.insertUserRoleByRoleId(roleList);
            }else {
                String password = user.getPassword();//初始密码
                String passwordRand = CommonConstant.INIT_PASSWORD_RAND;//处理秘钥因子
                String md5Password = MD5Util.generateMD5( password,passwordRand);
                user.setPassword(md5Password);
                user.setPasswordRand(passwordRand);
            }
            sysUserMapper.updateByPrimaryKeySelective(user);
        }
    }
    
    
    @Override
    @Transactional
    public void modifyUserStatusToFreeze(SysUser user, SysUser loginUser) {
        user.setStatus(CommonConstant.USER_STATUS_FREEZE);
        sysUserMapper.updateByPrimaryKeySelective(user);
        
    }

    @Override
    @Transactional
    public void modifyUserStatusToUnFreeze(SysUser user, SysUser loginUser) {
        user.setStatus(CommonConstant.USER_STATUS_YES_ACTIVATION);
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
	@Override
	public void modifyUserStatusToCancel(SysUser user, SysUser sessionUser) {
		user.setStatus(CommonConstant.USER_STATUS_CANCEL);
        sysUserMapper.updateByPrimaryKeySelective(user);
	}



    @Override
    public void modifyPassword(Map<String, Object> map)
        throws BusinessException {
        if (!(map.get("newPassword").toString()).equals(map.get("rePassword").toString())) {
            throw new BusinessException(ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL,
                    ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL_UNLIKE_MSG);
        }
        if (!checkUserPassword(map)) {
            throw new BusinessException(ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL,
                    ResponseCodeConstant.USER_PASSWORD_MODIFY_FAIL_NOTPASS_MSG);
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(map.get("userId").toString());
        if(user != null) {
            String md5Password = MD5Util.generateMD5(map.get("newPassword").toString(), user.getPasswordRand());
            SysUser tempUser = new SysUser();
            tempUser.setId(user.getId());
            tempUser.setPassword(md5Password);
            sysUserMapper.updateByPrimaryKeySelective(tempUser);
        }
        
    }
    public boolean checkUserPassword(Map<String, Object> map) {
        SysUser user = sysUserMapper.selectByPrimaryKey(map.get("userId").toString());
        if (user == null) {
            throw new BusinessException(ResponseCodeConstant.USER_LOGIN_FAIL, "用户不存在");
        }
        String md5Password = MD5Util.generateMD5(map.get("oldPassword").toString(), user.getPasswordRand());
        if (!user.getPassword().equals(md5Password)) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean checkMobileAndEmail(String email, String userId, String mobile) {
        int count = sysUserMapper.checkMobileAndEmail(CommonConstant.USER_STATUS_YES_ACTIVATION, email, mobile, Constants.BACKGROUNDCODE, userId);
        if (count > 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        int count = sysUserMapper.deleteByPrimaryKey(id);
        if (count > 0){
            return false;
        }
        return true;
    }

    @Override
    public void deleteByIds(List<String> ids) {
        sysUserMapper.batchDeleteUsers(ids);
    }

    @Override
    public GridModel<SysUser> queryByPage(String account,String identityCode, Integer pageNo, Integer pageSize, String sidx, String sord) {
        String orderString = "";
        if (!StringUtils.isEmpty(sidx)) {
            orderString = sidx + "." + sord;
        }
        PageBounds pageBounds = new PageBounds(pageNo, pageSize, Order.formString(orderString));
        PageList<SysUser> pageList =(PageList<SysUser>)sysUserMapper.queryByPage(account, identityCode, pageBounds);
        return new GridModel<SysUser>(pageList);
    }

    @Override
    public GridModel<Map<String, Object>> queryTimeExtraPage(String account, Integer pageNo, Integer pageSize, String sidx, String sord) {
        String orderString = "";
        if (!StringUtils.isEmpty(sidx)) {
            orderString = sidx + "." + sord;
        }
        PageBounds pageBounds = new PageBounds(pageNo, pageSize, Order.formString(orderString));
        PageList<Map<String, Object>> pageList =(PageList<Map<String, Object>>)sysUserMapper.queryTimeExtraPage(account, pageBounds);
        return new GridModel<>(pageList);
    }

    @Override
    public List<SysUser> queryList() {
        return sysUserMapper.queryList();
    }

    @Override
    @Transactional
    public boolean update(SysUser user, List<String> departIds,List<String> roleIds) {
        boolean a=true;
        List<SysUser> userList = sysUserMapper.selectAllEmail(user.getId());
        for(SysUser sysUser : userList){
            if (user.getEmail().equals(sysUser.getEmail())) {
                a = false;
            }
        }
        int result = sysUserMapper.checkMobileAndEmail(CommonConstant.USER_STATUS_YES_ACTIVATION, null, user.getMobile(),null,user.getId());
        if (result>0){
            a = false;
            throw new BusinessException("此手机号码已经被绑定");
        }
        if(a) {
            int i = sysUserMapper.updateByPrimaryKeySelective(user);
            userDepartmentInfoMapper.deleteByUserId(user.getId());
            sysUserRoleMapper.deleteUserRoleByUserId(user.getId());
            saveUserDeparts(user.getId(), user.getUpdateUser(), departIds);
            saveUserRole(user, user.getUpdateUser(), roleIds);
            return i > 0;
        }else{
            return a;
        }
    }





    @Override
    public SysUser getUserByIdentityCode(String identityCode) {
        SysUser sysUser = sysUserMapper.getUserByIdentityCode(identityCode);
        if(sysUser!=null) {
            sysUser.setPassword(null);
            sysUser.setPasswordRand(null);
        }
        return sysUser;
    }



    @Override
    @Transactional
    public void resetPassword(String userId,String sessionuserId) {
        if(null != userId){
           SysUser user= sysUserMapper.selectByPrimaryKey(userId);
            String randPwd =CommonConstant.INIT_PASSWORD_RAND;
            String pwd= MD5Util.generateMD5( "123456",randPwd);
            user.setPassword(pwd);
            user.setPasswordRand(randPwd);
            user.setUpdateUser(sessionuserId);
            user.setUpdateTime(new Date());
            sysUserMapper.updateByPrimaryKeySelective(user);
        }else{
            throw new BusinessException("密码重置失败，userId为空！");
        }
    }


    @Override
    @Transactional
    public void freezeUser(String userId,String status,String sessionUserId){
        if(null != userId){
            SysUser user= sysUserMapper.selectByPrimaryKey(userId);
            user.setStatus(Integer.parseInt(status));
            user.setUpdateUser(sessionUserId);
            user.setUpdateTime(new Date());
            sysUserMapper.updateByPrimaryKeySelective(user);
        }else{
            throw new BusinessException("操作失败，userId为空！");
        }
    }

    @Override
    public void updateUserInfo(SysUser sysUser) {
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


    @Override
    @Transactional
    public void updatePassword(String userId,String originalPwd,String newPwd,String conPwd,String sessionuserId){
        if(null != userId) {
            newPwd =newPwd.trim();
            conPwd =conPwd.trim();
            originalPwd=originalPwd.trim();
            String randPwd =CommonConstant.INIT_PASSWORD_RAND;
            SysUser user = sysUserMapper.selectByPrimaryKey(userId);
            originalPwd = MD5Util.generateMD5(originalPwd,randPwd);
           if(user.getPassword().equals(originalPwd)){
               if(newPwd.equals(conPwd)){
                   newPwd =MD5Util.generateMD5(newPwd,randPwd);
                   user.setPassword(newPwd);
                   user.setPasswordRand(randPwd);
                   user.setUpdateUser(sessionuserId);
                   user.setUpdateTime(new Date());
                   sysUserMapper.updateByPrimaryKeySelective(user);
               }else{
                   //抛出异常  新密码和确认密码 不一致
                   throw new BusinessException("新密码和确认密码 不一致");
               }
           }else{
               //抛出异常  原密码错误！
               throw new BusinessException("原密码错误");
           }
        }else{
            //抛出异常  userId为空！
            throw new BusinessException("userId为空！");
        }
    }

    @Override
    public GridModel<SysUser> queryContacts(String realName, Integer pageNo, Integer pageSize, String sidx, String sord) {
            String orderString = "create_time";
            PageBounds pageBounds = new PageBounds(pageNo, pageSize, Order.formString(orderString));
            PageList<SysUser> pageList =(PageList<SysUser>)sysUserMapper.queryContacts(realName, pageBounds);
            return new GridModel<SysUser>(pageList);
    }

    @Override
    public void register(SysUser user) {
        //前端传的是userName
        user.setMobile(user.getUserName());
        //校验手机号是否被注册
        int count = sysUserMapper.checkMobileAndEmail(CommonConstant.USER_STATUS_YES_ACTIVATION, null, user.getMobile(),null,null);
        if(count > 0){
            throw new BusinessException("此手机号码已经被注册");
        }
        user.setId(UUIDGenerator.getUUID());
        user.setCreateTime(new Date());
        String password = user.getPassword();//初始密码
        String passwordRand = CommonConstant.INIT_PASSWORD_RAND;//处理秘钥因子
        String md5Password = MD5Util.generateMD5( password,passwordRand);
        user.setPassword(md5Password);
        user.setPasswordRand(passwordRand);
        user.setStatus(CommonConstant.USER_STATUS_YES_ACTIVATION);
        sysUserMapper.insert(user);
        SysUserRole userRole = new SysUserRole();
        userRole.setId(UUIDGenerator.getUUID());
        userRole.setUserId(user.getId());
        userRole.setRoleId(SysRoleEnum.CUSTOMER.name());
        userRole.setCreateTime(new Date());
        sysUserRoleMapper.insert(userRole);
    }

    @Override
    public SysUser selectByAccount(String userName) {
        Map<String, Object> param = new HashMap<>();
        param.put("loginAccount", userName);
        return sysUserMapper.getUserByAccount(param);
    }
}

