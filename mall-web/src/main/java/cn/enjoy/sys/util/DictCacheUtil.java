/**
 * Created on 2015年9月2日 by Caiming
 */
package cn.enjoy.sys.util;




import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.sys.model.SelectModel;
import cn.enjoy.sys.model.SysDictData;
import cn.enjoy.sys.service.IDictDataService;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2009</p> <p>Company:享学信息科技有限公司
 * Co., Ltd.</p>

 * @version 1.0 修改记录： 修改序号，修改日期，修改人，修改内容
 */
@Component
public class DictCacheUtil {


    private static IDictDataService dictDataService;
    @Reference
    public void setDictDataService(IDictDataService dictDataService) {
        DictCacheUtil.dictDataService = dictDataService;
    }

    /**
     * <li>修改目的：【修改人：ZhouMin，修改时间：改为缓存取不到则调用接口获取】</li>
     * 
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<SelectModel> getDictDataSelectModelByType(String type) {
        List<SelectModel> list = dictDataService.findSelectModelsByType(type);
        return list;
    }

    /**
     * <li>修改目的：【修改人：ZhouMin，修改时间：改为缓存取不到则调用接口获取】</li>
     * 
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<SysDictData> getDictDatasByType(String type) {
        List<SysDictData> list = dictDataService.findDictByType(type);
        return list;
    }

    /**
     * @创建人 ZhouMin
     * @创建时间 2015年9月16日
     * @创建目的【根据type和code获取字典项数据】
     * @修改目的【修改人：ZhouMin，修改时间：改为缓存取不到则调用接口获取】
     * @param type
     * @param code
     * @return
     */
    public static SysDictData getDictDataByTypeAndCode(String type, String code) {
        List<SysDictData> dataList = getDictDatasByType(type);
        if (dataList != null) {
            for (SysDictData data : dataList) {
                if (data.getCode() != null && data.getCode().equals(code)) {
                    // 返回需要的字典项数据。
                    return data;
                }
            }
        }
        return null;
    }

    /**
     * @创建时间 2015年9月16日
     * @创建目的【根据type和cname获取字典项数据】
     * @修改目的【】
     * @param type
     * @param cname
     * @return
     */
    public static SysDictData getDictDataByTypeAndCName(String type, String cname) {
        List<SysDictData> dataList = getDictDatasByType(type);
        if (dataList != null) {
            for (SysDictData data : dataList) {
                if (data.getCname() != null && data.getCname().equals(cname)) {
                    // 返回需要的字典项数据。
                    return data;
                }
            }
        }
        return null;
    }

    /**
     * 根据类型和CODE获取CNAME
     * 
     * @param type
     *            the type
     * @param code
     *            the code
     * @return the string
     * @创建人 何睿
     * @创建时间 2016 -4-11 14:19:22
     */
    public static String getCnameByTypeAndCode(String type, String code) {
    	SysDictData data = getDictDataByTypeAndCode(type, code);
        if (data != null) {
            return data.getCname();
        } else {
            return code;
        }
    }

}
