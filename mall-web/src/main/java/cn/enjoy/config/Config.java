package cn.enjoy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author Ray
 * @date 2017/10/16
 */
@Component
public class Config {

    @Value("${dfs.url}")
    private String dfsUrl;

    @Value("${wx.appid}")
    private String wxAppid;

    @Value("${wx.redirect_uri}")
    private String wxRedirectUri;

    private CompanyConfig company;

    public String getDfsUrl() {
        return dfsUrl;
    }

    public void setDfsUrl(String dfsUrl) {
        this.dfsUrl = dfsUrl;
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxRedirectUri() {
        return wxRedirectUri;
    }

    public void setWxRedirectUri(String wxRedirectUri) {
        this.wxRedirectUri = wxRedirectUri;
    }

    public CompanyConfig getCompany() {
        return company;
    }

    @Resource
    public void setCompany(CompanyConfig company) {
        this.company = company;
    }
}
