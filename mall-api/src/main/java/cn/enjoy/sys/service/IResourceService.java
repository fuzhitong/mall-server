/**
 * Created on 2015年9月14日 by Caiming
 */
package cn.enjoy.sys.service;

import cn.enjoy.sys.model.ResourceTree;
import cn.enjoy.sys.model.SysResource;
import cn.enjoy.sys.model.TreeViewResource;

import cn.enjoy.core.utils.GridModel;

import java.util.List;
import java.util.Map;

public interface IResourceService {

    String getMenuIdByUrl(String url);

    /***
     * 获取资源树
     * <li>创建人：xiaopu</li>
     * <li>创建时间：2015年9月28日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @return
     */
    List<ResourceTree> queryResourceTree(String roleId, String parentId);

    List<ResourceTree> queryInitResourceTree(String roleId, String parentId);

    /***
     * 获取资源树
     * <li>创建人：maojia</li>
     * <li>创建时间：2015年10月28日</li>
     * <li>创建目的：【根据 菜单ID,获取 所有父节点数据】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @return
     */
    List<Map> queryResourceById(Map paramMap);

    /**
     *
     * @创建人：周礼
     * @创建时间：2015年11月26日
     * @创建目的：【】
     * @修改目的：【修改人：，修改时间：】
     * @param resource
     * @param page
     * @param rows
     * @param sidx
     *@param sord @return
     */
    GridModel<SysResource> loadResourceData(SysResource resource, int page, int rows, String sidx, String sord);

    /**
     * 菜单管理
     * @创建人：周礼
     * @创建时间：2015年11月26日
     * @创建目的：【】
     * @修改目的：【修改人：，修改时间：】
     * @param resource
     */
    void saveResource(SysResource resource);
    /**
     * 菜单管理
     * @创建人：周礼
     * @创建时间：2015年11月27日
     * @创建目的：【】
     * @修改目的：【修改人：，修改时间：】
     * @param id
     * @return
     */
    SysResource selectById(String id);

    /**
     * 菜单管理
     * @创建人：周礼
     * @创建时间：2015年11月27日
     * @创建目的：【】
     * @修改目的：【修改人：，修改时间：】
     * @param resOld
     * @return
     */
    String updateByPrimaryKey(SysResource resOld);

    /**
     *是否存在子菜单
     * @创建人：周礼
     * @创建时间：2015年11月27日
     * @创建目的：【】
     * @修改目的：【修改人：，修改时间：】
     * @param id
     * @return
     */
    boolean selectChildById(String id);

    void lockResource(String id);

    void lockResourceAndChild(String id);

    List<Map<String , Object>> selectParentName();
    /**
     * 菜单管理
     * @创建人：周礼
     * @创建时间：2015年12月4日
     * @创建目的：【通过父菜单id查询父菜单状态】
     * @修改目的：【修改人：，修改时间：】
     * @param parentId
     * @return
     */
    boolean selectParentStatusById(String parentId);
    /**
     * 菜单管理
     * @创建人：周礼
     * @创建时间：2015年12月4日
     * @创建目的：【激活】
     * @修改目的：【修改人：，修改时间：】
     * @param id
     */
    void unlockResource(String id);

    /***
     * 条件查询资源
     * @创建人 XX
     * @创建时间 2016年3月16日
     * @创建目的【】
     * @修改目的【修改人：，修改时间：】
     * @param map
     * @return
     */
    List<SysResource> selectResourceList(Map<String, Object> map);

    /**
     * 加载菜单树数据
     * @param resource Resource
     * @return list
     * @创建人 何睿
     */
    List<TreeViewResource> selectTreeViewResource(SysResource resource);

    void deleteByPrimaryKey(String id);

	List<SysResource> getResourceByUrl(String url);
	
    List<SysResource> getAllParentResourceListById(String id);

	List<SysResource> selectbyUserId(String sessionUserId);

	List<SysResource> selectBySysCode(String string);

    /**
     * 根据code查询resource,支付模糊匹配
     * @创建人 Ray
     * @创建日期 2016/11/7
     *
     * @param code  type of String
     * @return List<SysResource>
     */
	List<SysResource> selectResourceByCode(String code);

    /**
     * 通过父ID查询
     */
    List<SysResource> selectResourceByParentId(String parentId);

    void deleteById(String id);

    void deleteByIds(List<String> ids);

    List<String> selectResouceIdByRoleId(String roleId);

}

