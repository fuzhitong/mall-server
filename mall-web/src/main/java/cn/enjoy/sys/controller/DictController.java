package cn.enjoy.sys.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.sys.model.SelectModel;
import cn.enjoy.sys.model.SysDictData;
import cn.enjoy.sys.service.IDictDataService;
import cn.enjoy.sys.util.DictCacheUtil;
import cn.enjoy.core.utils.GridModel;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author Caiming
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
@RestController
@RequestMapping(value = "/api/dict")
public class DictController extends BaseController {
	
	@Reference
	private IDictDataService dictDataService;
	
	@RequestMapping(value = "/loadDictDataByType", method = {RequestMethod.GET, RequestMethod.POST})
	public HttpResponseBody<List<SelectModel>> loadDictDataByType(String type){
        List<SelectModel> models = DictCacheUtil.getDictDataSelectModelByType(type);
        return HttpResponseBody.successResponse("查询成功",models);
    }

	/**
	 * 
	 * @创建人 ZhouMin
	 * @创建时间 2015年9月28日
	 * @创建目的【根据types获取数据字典，如有多个type用逗号分隔】
	 * @修改目的【修改人：，修改时间：】
	 * @param types
	 * @return
	 */
    @RequestMapping(value = "/getDictDataByTypes", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<Map<String, List>> queryDictDataByTypes(String types) {
        if (StringUtils.isEmpty(types)) {
            return null;
        }
        Map<String, List> returnMap = new HashMap<String, List>();
        String[] typeStr = types.split(",");
        for (int i = 0; i < typeStr.length; i++ ) {
            List<SysDictData> dataList = DictCacheUtil.getDictDatasByType(typeStr[i]);
            if (dataList == null || dataList.isEmpty()) {
                dataList = dictDataService.findDictByType(typeStr[i]);
            }
            returnMap.put(typeStr[i], dataList);
        }
        return HttpResponseBody.successResponse("查询成功",returnMap);
    }
	
    @RequestMapping(value = "/getDictDataByType", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<List<SysDictData>> queryDictDataByType(@RequestParam String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        List<SysDictData> dataList = DictCacheUtil.getDictDatasByType(type);
        if (dataList == null || dataList.isEmpty()) {
            dataList = dictDataService.findDictByType(type);
        }
        return HttpResponseBody.successResponse("查询成功",dataList);
    }

    @RequestMapping(value = { "queryAllDictDataPage" }, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<GridModel<SysDictData>> queryAllDictTypePage(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "") String type, @RequestParam(required = false, defaultValue = "0") int page,
                                                                         @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "") String sidx, @RequestParam(required = false, defaultValue = "") String sord) {
        GridModel<SysDictData> SysDictGridModel = dictDataService.queryAllDictDataPage(param,type, page, pageSize, sidx, sord);
        return HttpResponseBody.successResponse("查询成功",SysDictGridModel);
    }

    @PostMapping("batchDeleteDictData")
    public HttpResponseBody batchDeleteDictData( @RequestParam(name="ids") String[] ids) {
        dictDataService.deleteByIds(Arrays.asList(ids));
        return HttpResponseBody.successResponse("删除成功");
    }

    @RequestMapping(value = "addDictData", method = RequestMethod.POST)
    public HttpResponseBody addDictData(SysDictData SysDictData) {
        dictDataService.addDictData(SysDictData);
        return HttpResponseBody.successResponse("新增成功");
    }

    @RequestMapping(value = { "detailDictData" }, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<Map<String,Object>> detailDictData(String id) {
        SysDictData sysDictData = dictDataService.selectByPrimaryKey(id);
        Map<String,Object> result = new HashMap<>();
        result.put("sysDictData",sysDictData);
        return HttpResponseBody.successResponse("查询成功",result);
    }

    @PostMapping("updateDictData")
    public HttpResponseBody updateDictData(SysDictData sysDictData) {
        dictDataService.updateDictData(sysDictData);
        return HttpResponseBody.successResponse("修改成功");
    }


    @PostMapping("deleteDictData")
    public HttpResponseBody deleteDictData(String id) {
        dictDataService.deleteDictData(id);
        return HttpResponseBody.successResponse("删除成功");
    }

    @PostMapping("freezeDictData")
    public HttpResponseBody freezeDictData(String id,String status) {
        dictDataService.freezeDictData(id,status);
        return HttpResponseBody.successResponse("操作成功");
    }
}

