package cn.enjoy.users.service.impl;

import cn.enjoy.users.dao.SysResourceMapper;
import cn.enjoy.users.dao.SysRoleResourceMapper;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.UUIDGenerator;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import cn.enjoy.sys.model.ResourceTree;
import cn.enjoy.sys.model.SysResource;
import cn.enjoy.sys.model.SysRoleResource;
import cn.enjoy.sys.model.TreeViewResource;
import cn.enjoy.sys.service.IResourceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author Ray
 *         Created on 2016/6/14.
 */
@Service
public class ResourceServiceImpl implements IResourceService {
    @Resource
    private SysResourceMapper sysResourceMapper;

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public String getMenuIdByUrl(String url){
        return sysResourceMapper.getIdByUrl(url);
    }


    @Override
    public List<ResourceTree> queryResourceTree(String roleId, String parentId){
        List<ResourceTree> resources;
        if(roleId != null &&!"".equals(roleId)){
            resources = sysResourceMapper.selectModifyResourceTree(roleId,parentId);
        }else{
            resources = sysResourceMapper.selectAddResourceTree(parentId);
        }
        return resources;
    }


    @Override
    public List<ResourceTree> queryInitResourceTree(String roleId,String parentId){
        List<ResourceTree> resources = null;
        if(roleId != null &&!"".equals(roleId)){
//            resources = sysResourceMapper.selectInitModifyResourceTree(roleId,parentId,sysCode,comType);
        }else{
            resources = sysResourceMapper.selectAddResourceTree(parentId);
        }
        return resources;
    }

    @Override
    public List<Map> queryResourceById(Map paramMap) {
        return this.sysResourceMapper.queryResourceById(paramMap);
    }



    @Override
    public GridModel<SysResource> loadResourceData(SysResource resource, int page, int rows, String sidx, String sord) {
        String orderString="";
        if(!StringUtils.isEmpty(sidx)){
            orderString = sidx +"." + sord;
        }else{
            orderString = "status.desc,sort_no";
        }
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderString));
        PageList<SysResource> pageList = sysResourceMapper.queryResourcePage(resource,pageBounds);
        return new GridModel<SysResource>(pageList);
    }

    @Override
    public void saveResource(SysResource resource){
        resource.setId(UUIDGenerator.getUUID());
        //新增资源
        if("".equals(resource.getParentId())){
            resource.setParentId(null);
        }
        sysResourceMapper.insertSelective(resource);

    }

    @Override
    public SysResource selectById(String id) {
        return sysResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public String updateByPrimaryKey(SysResource resOld) {
        sysResourceMapper.updateByPrimaryKeySelective(resOld);

        return null;
    }

    @Override
    public boolean selectChildById(String id) {
        if(sysResourceMapper.selectChildById(id) > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void lockResource(String id) {
        sysResourceMapper.lockByPrimaryKey(id);
    }

    @Override
    public void lockResourceAndChild(String id) {
        sysResourceMapper.lockResourceAndChild(id);
        sysResourceMapper.lockByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> selectParentName() {
        return sysResourceMapper.selectParentName();
    }

    @Override
    public boolean selectParentStatusById(String id) {
        int status = sysResourceMapper.selectParentStatusById(id);
        return status == 1;
    }

    @Override
    public void unlockResource(String id){
        sysResourceMapper.unlockResource(id);
    }



    @Override
    public List<SysResource> selectResourceList(Map<String,Object> map){
        return sysResourceMapper.selectResourceList(map);
    }

    @Override
    public List<TreeViewResource> selectTreeViewResource(SysResource resource) {
        return sysResourceMapper.selectTreeViewResource(resource);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        sysResourceMapper.deleteByPrimaryKey(id);
    }
    
    @Override
	public List<SysResource> getResourceByUrl(String url) {
		return sysResourceMapper.queryResourceByUrl(url);
	}
	
	/***
     * 
     * @创建人 XX
     * @创建时间 2016年3月16日
     * @创建目的【】
     * @修改目的【修改人：，修改时间：】
     * @param id
     * @return
     */
    @Override
    public List<SysResource> getAllParentResourceListById(String id){
        List<SysResource> allResourceList = sysResourceMapper.selectResourceList(new HashMap<String,Object>());
        List<SysResource> result_list = new ArrayList<SysResource>();
        for(SysResource r : allResourceList){
            if(r.getId().equals(id)){
            	result_list.add(r);
                if(!StringUtils.isEmpty(r.getParentId())){
                    getResourceListByParentId(r,result_list,allResourceList);
                }
            }
        }
        return result_list;
    }
    
    private void getResourceListByParentId(SysResource resource,List<SysResource> result_list,List<SysResource> allResourceList){
        for(SysResource r : allResourceList){
            if(r.getId().equals(resource.getParentId())){
            	result_list.add(r);
                if(!StringUtils.isEmpty(r.getParentId())){
                    getResourceListByParentId(r,result_list,allResourceList);
                }
            }
        }
    }


	@Override
	public List<SysResource> selectbyUserId(String sessionUserId) {
		
		return sysResourceMapper.selectbyUserId(sessionUserId);
	}

	@Override
	public List<SysResource> selectBySysCode(String string) {
		 
		return sysResourceMapper.sectbySysCode(string);
	}

    @Override
    public List<SysResource> selectResourceByCode(String code) {
        return sysResourceMapper.selectResourceByCode(code);
    }

    @Override
    public List<SysResource> selectResourceByParentId(String parentId) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("parentId",parentId);
        return sysResourceMapper.selectbyParentId(parentId);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        sysRoleResourceMapper.deleteByResourceId(id);
        sysResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<String> ids) {
        if(!CollectionUtils.isEmpty(ids)) {
            for(String id:ids){
                sysRoleResourceMapper.deleteByResourceId(id);
                sysResourceMapper.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<String> selectResouceIdByRoleId(String roleId) {
        List<SysRoleResource> sysRoleResources = sysRoleResourceMapper.selectResouceByRoleId(roleId);
        if(!CollectionUtils.isEmpty(sysRoleResources)) {
            return sysRoleResources.stream().map(res -> res.getResourceId()).collect(Collectors.toList());
        }
        return null;
    }
}

