package cn.enjoy.sys.service;

import cn.enjoy.sys.model.Oauth2Client;

import java.util.Map;

/**
 * @author Ray
 * @date 2018/3/23.
 */
public interface IAuthorizeService {


     boolean checkClientId(String clientId) ;


     boolean checkClientSecret(String clientSecret) ;


     long getExpireIn() ;

     Oauth2Client findClientByClientId(String clientId);

     /**
      * 查找所有的子系统
      * @return
      */
     Map<String, Oauth2Client> getClientMap();
}
