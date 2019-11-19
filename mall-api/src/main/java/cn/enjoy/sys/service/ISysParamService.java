package cn.enjoy.sys.service;

import cn.enjoy.sys.model.SysParam;

/**
 * Created by VULCAN on 2019/4/11.
 */
public interface ISysParamService {
    SysParam selectByCode(String code);
}
