package cn.enjoy.sys.service;


import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.sys.model.SysUser;
import cn.enjoy.core.utils.GridModel;

import java.util.List;
import java.util.Map;


public interface IUserService {

    /**
     *
     * @创建目的：【新增员工】
     * @修改目的：【修改人：，修改时间：】
     * @param user
     * @param sessionUserId
     */
    void addUser(SysUser user, String sessionUserId, List<String> departIds, List<String> roleIds);

    /**

     * @创建目的：【通过id查找员工】
     * @修改目的：【修改人：，修改时间：】
     * @param userId
     * @return
     */
    SysUser selectByPrimaryKey(String userId);


    /**
     *

     * @创建目的：【通过id查找员工】
     * @修改目的：【修改人：，修改时间：】
     * @param email
     * @return
     */
    SysUser selectByEmail(String email);

    /**
     *

     * @创建目的：【编辑员工】
     * @修改目的：【修改人：，修改时间：】
     * @param user
     */
    void editUser(SysUser user, String sessionUserId, String companyId);

    /**
     *
     * @创建目的：【冻结用户】
     * @修改目的：【修改人：，修改时间：】
     * @param user
     * @param sessionUser
     */
    void modifyUserStatusToFreeze(SysUser user, SysUser sessionUser);

    /**
     *
     * @创建目的：【解冻用户】
     * @修改目的：【修改人：，修改时间：】
     * @param user
     * @param sessionUser
     */
    void modifyUserStatusToUnFreeze(SysUser user, SysUser sessionUser);

    void saveUserRole(String roleId, List<Map<String, String>> map, String crateUserId, String companyId);

    boolean checkUserPassword(String userId, String password);

	void modifyUserPassWord(String userId, String oldPassword,
                            String newPassword, String repeatPassword);

    /***
     * <p><b>description:</b>忘记密码修改</p>
     * @param
     *        userId 用户ID
     *        newPassword    新密码
     *        repeatPassword 确认密码
     */
    void modifyUserForgetThePassword(String userId, String newPassword, String repeatPassword);


	void modifyUserMobile(String userId, String mobile);

	void modifyUserEmail(String userId, String email);

	boolean checkUniqueness(String accountCode);

	void modifyUserStatusToCancel(SysUser user, SysUser sessionUser);

    /**
     *
     * @创建人：周礼
     * @创建时间：2016年7月12日
     * @创建目的：【密码修改】
     * @修改目的：【修改人：，修改时间：】
     * @param map
     * @throws BusinessException
     */
    void modifyPassword(Map<String, Object> map) throws BusinessException;



    boolean checkMobileAndEmail(String email, String userId, String mobile);

    boolean deleteById(String id);

    void deleteByIds(List<String> ids);

    GridModel<SysUser> queryByPage(String account, String identityCode, Integer pageNo, Integer pageSize, String sidx, String sord);

    GridModel<Map<String,Object>> queryTimeExtraPage(String account, Integer pageNo, Integer pageSize, String sidx, String sord);


    List<SysUser> queryList();

    boolean update(SysUser user, List<String> departIds, List<String> roleIds);


    SysUser getUserByIdentityCode(String identityCode);


    void resetPassword(String userId, String sessionUserId);

    void updatePassword(String userId, String originalPwd, String newPwd, String conPwd, String sessionuserId);

    GridModel<SysUser> queryContacts(String realName, Integer pageNo, Integer pageSize, String sidx, String sord);


    void freezeUser(String userId, String status, String sessionUserId);


    void  updateUserInfo(SysUser sysUser);

    void register(SysUser user);

    SysUser selectByAccount(String userName);

}
