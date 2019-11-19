package cn.enjoy.sys.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Ray
 * @date 2018/3/23.
 */
public class OAuth2Token implements AuthenticationToken {

    private String authCode;
    private String principal;

    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAuthCode() {
        return authCode;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }
}
