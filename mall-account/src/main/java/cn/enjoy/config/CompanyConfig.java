package cn.enjoy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2017/12/15.
 */
@Component
public class CompanyConfig {
    @Value("${company.images.login-logo}")
    private String loginLogo;
    @Value("${company.images.logo}")
    private String logo;
    @Value("${company.images.login-bg}")
    private String loginBg;


    public String getLoginLogo() {
        return loginLogo;
    }

    public String getLogo() {
        return logo;
    }

    public String getLoginBg() {
        return loginBg;
    }
}
