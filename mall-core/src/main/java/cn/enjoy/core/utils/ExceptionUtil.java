/*
 * Created on 2016年2月25日 by ZhouMin
 */
package cn.enjoy.core.utils;

import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.response.ResponseCodeConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title 
 * @Description 
 * @Copyright Copyright (c) 2009</p>
 * @Company 享学信息科技有限公司 Co., Ltd.</p>
 * @author ZhouMin
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class ExceptionUtil {
    public static final String bizExceptionClassFullName= "com.bussiness.travel.common.exception.BusinessException";
    /**
     * 
     * @创建人 ZhouMin
     * @创建时间 2015年10月12日
     * @创建目的【判断是否是BussinessExceptio】
     * @修改目的【修改人：，修改时间：】
     * @param ex Exception
     * @return boolean
     */
    public static boolean isBussinessException(Exception ex) {
        if (!(ex instanceof RuntimeException)) {
            return false;
        }
        if (ex instanceof BusinessException) {// 非dubbo调用产生的异常
            return true;
        }
        // dubbo调用产生的异常
        String message = ex.getMessage();
        StackTraceElement[] stackArray = ex.getStackTrace();
        if (StringUtil.isNotEmpty(message) && stackArray != null && stackArray.length > 0) {
            String dubboExceptionFilter = "com.alibaba.dubbo.rpc.filter.ExceptionFilter";
            String stackArray0ClassName = stackArray[0].getClassName();
            int index = message.indexOf(':');
            if (dubboExceptionFilter.equals(stackArray0ClassName) && index > 1
                && bizExceptionClassFullName.equals(message.substring(0, index))) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, String> parseBusinessException(Exception ex) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", ResponseCodeConstant.SYSTEM_EXCEPTION_ERROR);
        String message = ex.getMessage();
        map.put("errorInfo", message);
        int indexResCodeStart = message.indexOf(':');
        int indexResCodeEnd = message.indexOf(BusinessException.resCodeSplitFlat);
        int indexMsgEnd = message.indexOf("\n");

        if (ex instanceof BusinessException) {// 非dubbo调用产生的异常
            map.put("code", ((BusinessException)ex).getCode());
            map.put("errorInfo",
                    message.substring(indexResCodeEnd + BusinessException.resCodeSplitFlat.length()));// 去掉code
            return map;
        }
        // dubbo调用产生的异常
        if (indexResCodeStart != -1 && indexResCodeEnd != -1 && indexMsgEnd != -1) {
            if (indexResCodeEnd > indexResCodeStart) {
                String rsCode = message.substring(indexResCodeStart + 1, indexResCodeEnd);
                map.put("code", rsCode);
            }
            if (indexMsgEnd > indexResCodeEnd) {
                String rsMsg = message.substring(indexResCodeEnd
                                + BusinessException.resCodeSplitFlat.length(),
                        indexMsgEnd);
                map.put("errorInfo", rsMsg);
            }
        }
        return map;
    }

    public static boolean isMobileDevice(String requestHeader){
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android","mac os","windows phone"};
        if(requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for(int i=0;i<deviceArray.length;i++){
            if(requestHeader.indexOf(deviceArray[i])>0){
                return true;
            }
        }
        return false;
    }

}

