package cn.enjoy.mall.service.impl;

import cn.enjoy.sys.model.SysParam;
import cn.enjoy.sys.service.ISysParamService;
import cn.enjoy.mall.dao.SysParamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysParamServiceImpl implements ISysParamService{

    @Resource
    private SysParamMapper sysParamMapper;

    @Override
    public SysParam selectByCode(String code) {
        return sysParamMapper.selectByCode(code);
    }
}
