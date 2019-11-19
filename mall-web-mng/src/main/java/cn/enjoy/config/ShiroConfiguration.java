package cn.enjoy.config;

import cn.enjoy.sys.security.ShiroAuthFilter;
import cn.enjoy.sys.security.ShiroRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author cl
 */
@Configuration
public class ShiroConfiguration {

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


    @Value("${shiro.maxAge.day}")
    private Integer maxAgeDay =  10;

    @Bean(name = "ShiroRealmImpl")
    public ShiroRealm getShiroRealm() {
        return new ShiroRealm();
    }



    @Bean(name = "securityManager")
    public DefaultWebSecurityManager  getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(getShiroRealm());
        dwsm.setRememberMeManager(rememberMeManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        shiroFilterFactoryBean.setLoginUrl("/api/system/unLogin");
        shiroFilterFactoryBean.setSuccessUrl("/api/system/logined");

        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/api/system/getFileServerUrl", "anon");
        filterChainDefinitionMap.put("/api/system/login", "anon");
        filterChainDefinitionMap.put("/api/system/unLogin", "anon");
        filterChainDefinitionMap.put("/api/system/accessDenied", "anon");
        filterChainDefinitionMap.put("/api/sys/config", "anon");
        filterChainDefinitionMap.put("/api/user/register", "anon");
        filterChainDefinitionMap.put("/api/wx/bind", "anon");
        filterChainDefinitionMap.put("/api/home/**", "anon");
        filterChainDefinitionMap.put("/wx/login", "anon");
        filterChainDefinitionMap.put("/api/system/logout", "anon");
        filterChainDefinitionMap.put("/api/logout", "logout");
        //配置记住我过滤器或认证通过可以访问的地址(当上次登录时，记住我以后，在下次访问/或/index时，可以直接访问，不需要登陆)
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**.js", "anon");
        filterChainDefinitionMap.put("/**.css", "anon");
        filterChainDefinitionMap.put("/**.html", "anon");
      //  filterChainDefinitionMap.put("/**", "user");
        filterChainDefinitionMap.put("/api/**", "accessPerms");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String,Filter> filterMap = new HashMap<>();
        ShiroAuthFilter shiroAuthFilter2 = getShiroAuthFilter();
        filterMap.put("accessPerms", shiroAuthFilter2);
        shiroFilterFactoryBean.setFilters(filterMap);
        //shiroFilterFactoryBean.setUnauthorizedUrl("/api/system/unLogin");
        return shiroFilterFactoryBean;
    }

    @Bean
    @Scope("prototype")
    public ShiroAuthFilter getShiroAuthFilter() {
        return  new ShiroAuthFilter();
    }


    @Bean
    public CookieRememberMeManager rememberMeManager(){
       // logger.info("注入Shiro的记住我(CookieRememberMeManager)管理器-->rememberMeManager", CookieRememberMeManager.class);
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,默认30天 ,单位秒：60 * 60 * 24 * 30
        simpleCookie.setMaxAge(60 * 60 * 24 * maxAgeDay);
        //simpleCookie.setMaxAge(60*1);

        return simpleCookie;
    }

//    @Bean
//    public SecurityManager securityManager() {
//        //logger.info("注入Shiro的Web过滤器-->securityManager", ShiroFilterFactoryBean.class);
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        //设置Realm，用于获取认证凭证
//        securityManager.setRealm(getShiroRealm());
//        //注入缓存管理器
//        securityManager.setCacheManager(getEhCacheManager());
//        //注入Cookie(记住我)管理器(remenberMeManager)
//        securityManager.setRememberMeManager(rememberMeManager());
//
//        return securityManager;
//    }

}