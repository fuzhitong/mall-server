package cn.enjoy.sys.controller;

import cn.enjoy.sys.model.Department;
import cn.enjoy.sys.model.SysRole;
import cn.enjoy.sys.model.SysUser;
import cn.enjoy.sys.service.IDepartmentService;
import cn.enjoy.sys.service.IRoleService;
import cn.enjoy.sys.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenlin
 */
@RestController
@RequestMapping("/api/user/")
public class UserController  extends BaseController {


    @Reference
    private IRoleService iRoleService;

    @Reference
    private IUserService iUserService;

    @Reference
    private IDepartmentService iDepartmentService;


    @PostMapping("addUser")
    public  HttpResponseBody addUser(SysUser user, @RequestParam(name="departIds",required = false,defaultValue = "") String[] departIds, @RequestParam(name="roleIds",required = false,defaultValue = "") String[] roleIds) {
       iUserService.addUser(user,getSessionUserId(), Arrays.asList(departIds),Arrays.asList(roleIds));
        return HttpResponseBody.successResponse("新增成功");
    }


    @GetMapping("queryUserPage")
    public HttpResponseBody<GridModel<SysUser>> searchUsers(@RequestParam(required = false, defaultValue = "") String param,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                            @RequestParam(required = false, defaultValue = "") String sidx,
                                                            @RequestParam(required = false, defaultValue = "") String sord,
                                                            @RequestParam(required = false, defaultValue = "1")String identityCode) {
        GridModel<SysUser> sysUserGridModel = iUserService.queryByPage(param, identityCode, page, pageSize, sidx, sord);
        //return sysUserGridModel;
        return HttpResponseBody.successResponse("查询成功",sysUserGridModel);
    }

    @GetMapping("queryUserList")
    public HttpResponseBody<List<SysUser>> searchUserList() {
        List<SysUser> sysUsers = iUserService.queryList();
        return HttpResponseBody.successResponse("查询成功",sysUsers);
    }

    @GetMapping("detailUser" )
    public HttpResponseBody<Map<String,Object>> detailUser(String id) {
        SysUser sysUser = iUserService.selectByPrimaryKey(id);
        List<Department> departments = iDepartmentService.selectByUserId(id);
        List<SysRole> list = iRoleService.queryUserRoleByUserId(id);
        if(sysUser!=null) {
            sysUser.setPassword(null);
            sysUser.setPasswordRand(null);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("userInfo",sysUser);
        result.put("departments",departments);
        result.put("roleNames",list);
        return HttpResponseBody.successResponse("查询成功",result);
    }



    @PostMapping("deleteUser")
    public HttpResponseBody delete(String id) {
        iUserService.freezeUser(id,"0",getSessionUserId());
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("updateUser")
    public HttpResponseBody update(SysUser user, @RequestParam(name="departIds",required = false,defaultValue = "") String[] departIds,
                         @RequestParam(name="roleIds",required = false,defaultValue = "") String[] roleIds) {
        user.setUpdateUser(this.getSessionUserId());
        boolean result = iUserService.update(user,Arrays.asList(departIds),Arrays.asList(roleIds));
        if(result){
            return HttpResponseBody.successResponse("修改成功");
        }else {
            return HttpResponseBody.failResponse("修改失败,用户邮箱不能与其它用户重复");
        }
    }



    @PostMapping("batchDeleteUser")
    public HttpResponseBody batchDelete( @RequestParam(name="ids") String[] ids) {
        iUserService.deleteByIds(Arrays.asList(ids));
        return HttpResponseBody.successResponse("删除成功");
    }





    @RequestMapping(value = "getUserByIdentityCode", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<SysUser>  getUserByIdentityCode(String identityCode) {
        SysUser user = iUserService.getUserByIdentityCode(identityCode);
        return HttpResponseBody.successResponse("查询成功",user);
    }


    //工时补录查询
    @GetMapping("queryTimeExtraPage")
    public HttpResponseBody<GridModel<Map<String,Object>>> queryTimeExtralPage(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "0") int page,
                                                                               @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "") String sidx, @RequestParam(required = false, defaultValue = "") String sord) {
        GridModel<Map<String,Object>> sysUserGridModel = iUserService.queryTimeExtraPage(param, page, pageSize, sidx, sord);
        return HttpResponseBody.successResponse("查询成功",sysUserGridModel);
    }



    //重置密码
    @PostMapping("resetPassword")
    public HttpResponseBody resetPassword(String userId) {
        iUserService.resetPassword(userId,getSessionUserId());
        return HttpResponseBody.successResponse("密码重置成功");
    }
    //密码修改
    @PostMapping("updatePassword")
    public HttpResponseBody updatePassword(String originalPwd,String newPwd,String conPwd) {
        iUserService.updatePassword(getSessionUserId(),originalPwd,newPwd,conPwd,getSessionUserId());
        return HttpResponseBody.successResponse("密码修改成功");
    }

    //通讯录查询
    @GetMapping("queryContactsPage")
    public HttpResponseBody<GridModel<SysUser>> queryContactsPage(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "") String sidx, @RequestParam(required = false, defaultValue = "") String sord) {
        GridModel<SysUser> list = iUserService.queryContacts(param, page,pageSize,sidx,sord);
        return HttpResponseBody.successResponse("查询成功",list);
    }

    //冻结2 or  解冻1 用户
    @RequestMapping(value = "freezeUser", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody freezeUser(String userId,String status) {

        iUserService.freezeUser(userId,status,getSessionUserId());
        if("2".equals(status)){
            return HttpResponseBody.successResponse("冻结成功");
        }else{
            return HttpResponseBody.successResponse("解冻成功");
        }

    }

    @PostMapping("register")
    public HttpResponseBody register(SysUser sysUser){
        iUserService.register(sysUser);
        return HttpResponseBody.successResponse("注册成功");
    }

    @GetMapping("getUserInfo")
    public HttpResponseBody getUserInfo(){
        SysUser sysUser = this.getSessionUser();
        Map<String, Object> result = new HashMap<>();
        result.put("userInfo", sysUser);
        return HttpResponseBody.successResponse("查询成功", result);
    }

}
