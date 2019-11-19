package cn.enjoy.sys.controller;

import cn.enjoy.sys.model.TreeViewResource;
import cn.enjoy.sys.model.SysResource;
import cn.enjoy.sys.service.IResourceService;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.response.HttpResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/resource/")
public class ResourceController extends BaseController {

    @Reference
    private IResourceService iResourceService;

    @PostMapping("addResource")
    public HttpResponseBody addResource(SysResource resource) {
        resource.setStatus(1);
        resource.setCreateTime(new Date());
        resource.setCreateUser(getSessionUserId());
        resource.setUpdateTime(new Date());
        resource.setUpdateUser(getSessionUserId());
        iResourceService.saveResource(resource);
        return HttpResponseBody.successResponse("新增成功");
    }

    @GetMapping("queryResourcePage")
    public HttpResponseBody<GridModel<SysResource>> queryResourcePage(SysResource resource, @RequestParam(required = false, defaultValue = "0") int page,
                                                                      @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "") String sidx, @RequestParam(required = false, defaultValue = "") String sord) {
        GridModel<SysResource> sysUserGridModel = iResourceService.loadResourceData(resource, page, pageSize, sidx, sord);
        return HttpResponseBody.successResponse("查询成功", sysUserGridModel);
    }

    @GetMapping("queryResourceTree")
    public HttpResponseBody<List<TreeViewResource>> queryResourceTree() {
        List<TreeViewResource> treeViewResources = iResourceService.selectTreeViewResource(new SysResource());
        return HttpResponseBody.successResponse("查询成功", treeViewResources);
    }

    @PostMapping("deleteResource")
    public HttpResponseBody delete(String id) {
        iResourceService.deleteById(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("updateResource")
    public HttpResponseBody update(SysResource resource) {
        SysResource resOld = iResourceService.selectById(resource.getId());
        resOld.setSortNo(resource.getSortNo());
        resOld.setName(resource.getName());
        resOld.setCode(resource.getCode());

        if (StringUtils.isEmpty(resource.getParentId())) {
            resOld.setParentId(null);
        } else {
            resOld.setParentId(resource.getParentId());
        }
        resOld.setIdentifying(resource.getIdentifying());
        resOld.setUrl(resource.getUrl());
        resOld.setType(resource.getType());
        resOld.setIcon(resource.getIcon());
        resOld.setUpdateTime(new Date());
        resOld.setUpdateUser(getSessionUserId());
        iResourceService.updateByPrimaryKey(resOld);
        return HttpResponseBody.successResponse("操作成功");
    }

    @GetMapping("getResourceOptions")
    public HttpResponseBody<List<Map<String, Object>>> getResourceOptions() {
        List<Map<String, Object>> parentIdList = iResourceService.selectParentName();
        return HttpResponseBody.successResponse("查询成功", parentIdList);
    }

    @PostMapping("batchDeleteResource")
    public HttpResponseBody batchDelete(@RequestParam(name = "ids") String[] ids) {
        iResourceService.deleteByIds(Arrays.asList(ids));
        return HttpResponseBody.successResponse("删除成功");
    }

}
