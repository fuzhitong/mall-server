package cn.enjoy.sys.controller;



import cn.enjoy.sys.service.IDictTypeService;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.sys.model.SysDictType;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/dictType")
public class DictTypeController extends BaseController {
	
    @Reference
    private IDictTypeService dictTypeService;

    @GetMapping("queryAllDictTypePage")
    public HttpResponseBody<GridModel<SysDictType>> queryAllDictTypePage(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "0") int page,
                                                                         @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "") String sidx, @RequestParam(required = false, defaultValue = "") String sord) {
        GridModel<SysDictType> SysDictGridModel = dictTypeService.queryAllDictTypePage(param, page, pageSize, sidx, sord);
        return HttpResponseBody.successResponse("查询成功",SysDictGridModel);
    }

    @PostMapping("batchDeleteDictType")
    public HttpResponseBody batchDeleteDictType( @RequestParam(name="ids") String[] ids) {
        dictTypeService.deleteByIds(Arrays.asList(ids));
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("addDictType")
    public HttpResponseBody addDictType(SysDictType sysDictType) {
        dictTypeService.addDictType(sysDictType);
        return HttpResponseBody.successResponse("新增成功");
    }

    @GetMapping("detailDictType")
    public HttpResponseBody detailDictType(String id) {
        SysDictType sysDictType = dictTypeService.selectByPrimaryKey(id);
        Map<String,Object> result = new HashMap();
        result.put("SysDictType",sysDictType);
        return HttpResponseBody.successResponse("查询成功",result);
    }

    @PostMapping("deleteDictType")
    public HttpResponseBody deleteDictType(String id) {
        dictTypeService.deleteDictType(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("updateDictType")
    public HttpResponseBody updateDictType(SysDictType sysDictType) {
        dictTypeService.updateDictType(sysDictType);
        return HttpResponseBody.successResponse("修改成功");
    }

    @PostMapping("freezeDictType")
    public HttpResponseBody freezeDictType(String id,String status) {
        dictTypeService.freezeDictTyp(id,status);
        return HttpResponseBody.successResponse("操作成功");
    }
}

