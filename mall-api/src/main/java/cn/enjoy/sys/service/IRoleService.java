/**
 * Created on 2015年9月9日 by Caiming
 */
package cn.enjoy.sys.service;


import cn.enjoy.sys.model.SysRole;
import cn.enjoy.sys.model.SelectModel;
import cn.enjoy.core.utils.GridModel;

import java.util.List;

public interface IRoleService {

    /***
     * 根据角色ID 查询角色和模块组
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月10日</li>
    List<SelectModel> findRolesSelectModel(String sessionUserCompanyId, int parseInt);

//    List<SelectModel> findRolesSelectModel(String companyId,Integer loginType);
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param role
     * @return
     */
    public SysRole selectRoleById(SysRole role);

    /***
     * 新增角色和业务模块关联
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月10日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param role
     * @param resourceIds
     * @return
     */
    public String addRole(SysRole role, String resourceIds);

    public String addRoleXA(SysRole role, String resourceIds);

    /**
     * 编辑角色和角色业务模块关联
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月10日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param role
     * @param resourceId
     * @return
     */
    public String modifyRole(SysRole role, String resourceId);

    public String modifyRoleXA(SysRole role, String resourceId);

    /***
     * 删除角色并删除角色业务模块关联
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月10日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param roleId
     * @return
     */
    public String deleteRoleById(String roleId);
    /***
     * 根据ID查询角色明细
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月29日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param roleId
     * @return
     */
    SysRole queryRoleDetailById(String roleId);


    /**
     * 根据用户ID和资源ID查询角色
     * <li>创建人：Caiming</li>
     * <li>创建时间：2015年10月20日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param userId
     * @param resourceId
     * @return
     */
    List<SysRole> findRoleByResourceId(String userId, String resourceId);


    /***
     * 新增初始化角色和业务模块关联
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月10日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param initRole
     * @param resourceIds
     * @return String
     */
    public String saveInitRole(SysRole initRole, String resourceIds);


    public String modifyInitRole(SysRole initRole, String resourceIds);

    public String deleteInitRoleById(String roleId);

    List<SysRole> queryUserRoleByUserId(String userId);

    SysRole selectByRoleCode(String roleCode);

	void addDefaultRoles(String userid);
	String insertRole(SysRole role, String resourceIds);

	SysRole selectRoleById(String id);
	List<SelectModel> selectDefaultRole();

    public GridModel<SysRole> selectRolePage(String param, int pageNum, int pageSize, String sidx, String sord);

    void deleteByIds(List<String> ids);

    List<SysRole> queryRoleList();
}

