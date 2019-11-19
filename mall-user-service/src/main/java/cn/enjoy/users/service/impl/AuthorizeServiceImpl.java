package cn.enjoy.users.service.impl;

import cn.enjoy.users.dao.Oauth2ClientMapper;
import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.sys.model.Oauth2Client;
import cn.enjoy.sys.service.IAuthorizeService;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AuthorizeServiceImpl implements IAuthorizeService {

    @Resource
    private Oauth2ClientMapper oauth2ClientMapper;

    @Override
    public boolean checkClientId(String clientId) {
        return oauth2ClientMapper.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return oauth2ClientMapper.findBySecret(clientSecret) != null;
    }

    @Override
    public long getExpireIn() {
        return 3600L;
    }

    @Override
    public Oauth2Client findClientByClientId(String clientId) {
        return oauth2ClientMapper.findByClientId(clientId);
    }

    @Override
    public Map<String, Oauth2Client> getClientMap(){
        return oauth2ClientMapper.getAll4Map();
    }
}
