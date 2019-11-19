package cn.enjoy.users.service.impl;

import cn.enjoy.users.dao.DepartmentMapper;
import cn.enjoy.users.dao.UserDepartmentInfoMapper;
import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.CommonConstant;
import cn.enjoy.core.utils.UUIDGenerator;
import com.alibaba.dubbo.config.annotation.Service;

import cn.enjoy.sys.model.Department;
import cn.enjoy.sys.model.DepartmentTree;
import cn.enjoy.sys.model.UserDepartmentInfo;
import cn.enjoy.sys.service.IDepartmentService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	
	@Resource
	private DepartmentMapper deptmapper;

	@Resource
	private UserDepartmentInfoMapper userDepartmentInfoMapper;



	/**
	 * 数据库中没有排布
	 */
	@Override
	public Department saveDepartment(Department dept){
		dept.setId(UUIDGenerator.getUUID());
		dept.setStatus(String.valueOf(CommonConstant.VALID));
		if(deptmapper.isExistSameName(dept) > 0){
			throw  new BusinessException("存在同名兄弟部门！");
		}

		deptmapper.insert(dept);
		return dept;
	}

	@Override
	public void editDepartment(Department dept) {
		deptmapper.updateByPrimaryKeySelective(dept);
	}

	@Override
	public List<Department> loadDepartment(Department dept) {
		return deptmapper.loadDepartment(dept);
	}

	@Override
	public void removeDepartment(String id) throws BusinessException{
		   
		Department dept = deptmapper.selectByPrimaryKey(id);
		List<UserDepartmentInfo> userDepartmentInfos = userDepartmentInfoMapper.selectByDepartmentId(id);
		 if(!CollectionUtils.isEmpty(userDepartmentInfos)){
				throw  new BusinessException("请先将该部门下的所有员工删除再进行此操作！");
		 }
		dept.setStatus(String.valueOf(CommonConstant.INVALID));
		deptmapper.updateByPrimaryKeySelective(dept);
	}

	@Override
	public Department selectDepartmentById(String id) {
		 return deptmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DepartmentTree> selectByToTree(String deptName,String parentId) {
		List<DepartmentTree> tree = deptmapper.selectToTree(deptName,parentId);
		return tree;
	}

	@Override
	public List<Department> selectByUserId(String userId) {
		return  deptmapper.selectByUserId(userId);
	}

}
