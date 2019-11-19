package cn.enjoy.mall.web.controller;


import cn.enjoy.sys.model.DepartmentTree;
import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.sys.controller.BaseController;
import cn.enjoy.sys.model.Department;
import cn.enjoy.sys.model.SelectModel;
import cn.enjoy.sys.service.IDepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author chenlin
 */
@RestController
@RequestMapping("/api/department/")
public class DepartmentController extends BaseController {

    @Reference
    private IDepartmentService iDepartmentService;

    @PostMapping("addDepartment")
    public HttpResponseBody addDepartment(Department dept) {
        iDepartmentService.saveDepartment(dept);
        return HttpResponseBody.successResponse("新增成功");
    }

    @PostMapping("saveDept")
    public HttpResponseBody saveDept(String deptName, String parentId, String deptId) {
        Department department = new Department();
        department.setDepartmentName(deptName);
        department.setId(deptId);
        department.setParentDepartment(parentId);

        if (parentId.equals(deptId)) {
            iDepartmentService.saveDepartment(department);
        } else {
            iDepartmentService.editDepartment(department);
        }
        return HttpResponseBody.successResponse("保存成功");
    }

    @PostMapping("deleteDepartment")
    public HttpResponseBody delete(String id) {
        iDepartmentService.removeDepartment(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("updateDepartment")
    public HttpResponseBody update(Department department) {
        iDepartmentService.editDepartment(department);
        return HttpResponseBody.successResponse("修改成功");
    }

    @GetMapping("queryDepartmentTree")
    public HttpResponseBody<List<DepartmentTree>> queryToTree(@RequestParam(required = false, defaultValue = "") String deptName) {
        List<DepartmentTree> departments = iDepartmentService.selectByToTree(deptName, null);
        return HttpResponseBody.successResponse("查询成功", departments);
    }

    @GetMapping("getDepartmentOptions")
    public HttpResponseBody<List<SelectModel>> queryDepartment() {
        List<Department> departments = iDepartmentService.loadDepartment(null);
        List<SelectModel> result = new ArrayList<>();
        for (Department depart : departments) {
            SelectModel sm = new SelectModel();
            sm.setKey(depart.getId());
            sm.setValue(depart.getDepartmentName());
            result.add(sm);
        }
        return HttpResponseBody.successResponse("查询成功", result);
    }


}
