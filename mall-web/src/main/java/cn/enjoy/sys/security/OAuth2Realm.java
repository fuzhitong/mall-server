package cn.enjoy.sys.security;


import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.CommonConstant;
import cn.enjoy.core.utils.JsonUtils;
import cn.enjoy.core.utils.response.ResponseCodeConstant;
import cn.enjoy.sys.model.SysUser;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author Ray
 * @date 2018/3/23.
 */
public class OAuth2Realm extends AuthorizingRealm {


    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;
    private String userInfoUrl;
    private String redirectUrl = "";

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token; // 仅支持OAuth2Token类型。
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2Token oAuth2Token = (OAuth2Token) token;
        String code = oAuth2Token.getAuthCode();
        try {
            String username = extractUsername(code);

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, code, getName());

            return authenticationInfo;
        } catch (Exception e) {
            throw new AuthenticationException("认证出错", e);
        }
    }

    private String extractUsername(String code) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest accessTokenRequest = OAuthClientRequest.tokenLocation(accessTokenUrl)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setCode(code)
                    .setRedirectURI(redirectUrl)
                    .setParameter("sid", SecurityUtils.getSubject().getSession().getId().toString())
                    .buildQueryMessage();
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();

            //拿用户信息
            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl)
                    .setAccessToken(accessToken).buildQueryMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

            String userJson = resourceResponse.getBody();
            SysUser user = JsonUtils.json2Obj(userJson, SysUser.class);
            this.setResource(user, accessToken);
            return user.getUserName();
        } catch(OAuthSystemException e) {
            e.printStackTrace();
            throw new  RuntimeException(e);
        } catch(OAuthProblemException e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCodeConstant.UN_LOGIN_ERROR, "没有登录");
        }
    }


    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    private void setResource( SysUser user, String accessToken){
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(CommonConstant.SESSION_USER_KEY, user);
        session.setAttribute(CommonConstant.SESSION_USER_NAME_KEY, user.getUserName());
        session.setAttribute(CommonConstant.SESSION_USER_ID_KEY, user.getId());
        session.setAttribute(CommonConstant.SESSION_USER_REAL_NAME_KEY, user.getRealName());
        session.setAttribute(CommonConstant.SESSION_ACCESS_TOKEN, accessToken);
    }

}
