package cn.enjoy.sys.service;

import cn.enjoy.sys.model.DepartmentTree;
import cn.enjoy.sys.model.Department;

import java.util.List;

public interface IDepartmentService {

	Department saveDepartment(Department dept);

	void editDepartment(Department dept);

	List<Department> loadDepartment(Department dept);

	void removeDepartment(String id);

	Department selectDepartmentById(String id);

	List<DepartmentTree> selectByToTree(String deptName, String parentId);

	List<Department> selectByUserId(String userId);

}
