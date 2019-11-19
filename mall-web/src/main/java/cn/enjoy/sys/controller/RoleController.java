package cn.enjoy.sys.controller;

import cn.enjoy.sys.model.SysRole;
import cn.enjoy.sys.service.IResourceService;
import cn.enjoy.sys.service.IRoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/role/")
public class RoleController extends BaseController {

    @Reference
    private IRoleService iRoleService;

    @Reference
    private IResourceService iResourceService;

    @PostMapping("addRole")
    public HttpResponseBody addRole(SysRole role, @RequestParam(name = "resources", required = false, defaultValue = "") String[] resources) {
        role.setCreateTime(new Date());
        role.setCreateUser(getSessionUserId());
        role.setUpdateTime(new Date());
        role.setUpdateUser(getSessionUserId());
        role.setRoleName(role.getRoleName().replaceAll(" ", ""));

        String s = StringUtils.arrayToCommaDelimitedString(resources);
        iRoleService.addRole(role, s);
        return HttpResponseBody.successResponse("新增成功");
    }

    @GetMapping("queryRolePage")
    public HttpResponseBody<GridModel<SysRole>> searchRole(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "") String sidx, @RequestParam(required = false, defaultValue = "") String sord) {
        GridModel<SysRole> sysUserGridModel = iRoleService.selectRolePage(param, page, pageSize, sidx, sord);
        return HttpResponseBody.successResponse("查询成功", sysUserGridModel);
    }

    @PostMapping("deleteRole")
    public HttpResponseBody delete(String id) {
        iRoleService.deleteRoleById(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("updateRole")
    public HttpResponseBody update(String id, String roleName, String roleCode, String roleDesc, @RequestParam(name = "resources", required = false, defaultValue = "") String[] resources) {
        SysRole role = new SysRole();
        role.setUpdateTime(new Date());
        role.setRoleName(roleName.replaceAll(" ", ""));
        if (!StringUtils.isEmpty(roleCode)) {
            role.setRoleCode(roleCode.replaceAll(" ", ""));
        }
        role.setUpdateUser(getSessionUserId());
        role.setRoleDesc(roleDesc);
        String resourceIds = StringUtils.arrayToCommaDelimitedString(resources);
        if (!StringUtils.isEmpty(id)) {//修改
            role.setId(id);
            iRoleService.modifyRole(role, resourceIds);
        } else {
            role.setCreateTime(new Date());
            role.setCreateUser(getSessionUserId());
            iRoleService.addRole(role, resourceIds);
        }
        return HttpResponseBody.successResponse("操作成功");
    }


    @PostMapping("batchDeleteRole")
    public HttpResponseBody batchDelete(@RequestParam(name = "ids") String[] ids) {
        iRoleService.deleteByIds(Arrays.asList(ids));
        return HttpResponseBody.successResponse("删除成功");
    }

    @GetMapping("detailRole")
    public HttpResponseBody<Map<String, Object>> detailRole(String id) {
        SysRole sysRole = iRoleService.selectRoleById(id);
        // List<ResourceTree> resourceTrees = iResourceService.queryResourceTree(id, null);
        List<String> resources = iResourceService.selectResouceIdByRoleId(id);
        Map<String, Object> result = new HashMap<>();
        result.put("roleInfo", sysRole);
        result.put("resourceInfo", resources);
        return HttpResponseBody.successResponse("查询成功", result);
    }

    @GetMapping("queryRoleList")
    public HttpResponseBody<List<SysRole>> queryRoleList() {
        List<SysRole> list = iRoleService.queryRoleList();
        return HttpResponseBody.successResponse("查询成功", list);
    }

    /**
     * 根据用户id 查询角色信息
     */
    @GetMapping("queryRoleName")
    public HttpResponseBody<List<SysRole>> queryRoleName() {
        List<SysRole> list = iRoleService.queryUserRoleByUserId(getSessionUserId());
        return HttpResponseBody.successResponse("查询成功", list);
    }
}
