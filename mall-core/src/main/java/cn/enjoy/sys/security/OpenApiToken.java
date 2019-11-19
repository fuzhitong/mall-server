package cn.enjoy.sys.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by admin on 2017/8/16.
 */
public class OpenApiToken extends UsernamePasswordToken {

    public static String passWrod = "12346578";

    public String getPassWrod() {
        return passWrod;
    }

    private String userId;

    public OpenApiToken(String userId, boolean rememberMe) {
        super(userId, passWrod, rememberMe);
        super.setRememberMe(rememberMe);
        this.userId = userId;
        this.setUsername(userId);
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
