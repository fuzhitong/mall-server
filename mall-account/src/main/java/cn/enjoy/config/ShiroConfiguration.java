package cn.enjoy.config;

import cn.enjoy.mall.constant.SsoConstants;
import cn.enjoy.sys.security.MySessionListener;
import cn.enjoy.sys.security.ShiroAuthFilter;
import cn.enjoy.sys.security.ShiroRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;

import javax.servlet.Filter;
import java.util.*;

/**
 *
 * @author cl
 */
@Configuration
public class ShiroConfiguration {

    @Value("${shiro.maxAge.day}")
    private Integer maxAgeDay =  10;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;


    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(7200);// 配置缓存过期时间
        redisManager.setPassword(password);
        return redisManager;
    }

 /*   @Bean
    public CredentialsMatcher credentialsMatcher(){
        RetryLimitHashedCredentialsMatcher matcher =  new RetryLimitHashedCredentialsMatcher(getEhCacheManager());
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }*/

    @Bean(name = "ShiroRealmImpl")
    public AuthorizingRealm getShiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        return realm;
    }





    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    @Bean(name = "securityManager")
    public WebSecurityManager webSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(getShiroRealm());
        dwsm.setCacheManager(cacheManager());
        dwsm.setRememberMeManager(rememberMeManager());
        dwsm.setSessionManager(sessionManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(webSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager());
        shiroFilterFactoryBean.setLoginUrl("/api/system/unLogin");
        shiroFilterFactoryBean.setSuccessUrl("/api/system/logined");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/api/system/getFileServerUrl", "anon");
        filterChainDefinitionMap.put("/api/system/login", "anon");
        filterChainDefinitionMap.put("/api/system/unLogin", "anon");
        filterChainDefinitionMap.put("/api/system/accessDenied", "anon");
        filterChainDefinitionMap.put("/api/sys/**", "anon");
        filterChainDefinitionMap.put("/api/user/register", "anon");
        filterChainDefinitionMap.put("/api/wx/bind", "anon");
        filterChainDefinitionMap.put("/api/home/**", "anon");
        filterChainDefinitionMap.put("/api/authorize", "anon");
        filterChainDefinitionMap.put("/api/accessToken", "anon");
        filterChainDefinitionMap.put("/api/userInfo", "anon");
        filterChainDefinitionMap.put("/api/user/register", "anon");

        filterChainDefinitionMap.put("/wx/login", "anon");
        filterChainDefinitionMap.put("/api/system/logout", "anon");
        filterChainDefinitionMap.put("/api/logout", "anon");
        //配置记住我过滤器或认证通过可以访问的地址(当上次登录时，记住我以后，在下次访问/或/index时，可以直接访问，不需要登陆)
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**.js", "anon");
        filterChainDefinitionMap.put("/**.css", "anon");
        filterChainDefinitionMap.put("/**.html", "anon");
      //  filterChainDefinitionMap.put("/**", "user");
        filterChainDefinitionMap.put("/api/**", "accessPerms");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String,Filter> filterMap = new HashMap<>();
        AuthenticationFilter shiroAuthFilter2 = getShiroAuthFilter();
        filterMap.put("accessPerms", shiroAuthFilter2);
        shiroFilterFactoryBean.setFilters(filterMap);
        //shiroFilterFactoryBean.setUnauthorizedUrl("/api/system/unLogin");
        return shiroFilterFactoryBean;
    }

    @Bean
    @Scope("prototype")
    public AuthenticationFilter getShiroAuthFilter() {
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


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(webSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public SimpleCookie simpleCookie(){
        return new SimpleCookie(SsoConstants.SSO_SESSION_ID);
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }

    /**
     * session监听
     * 注意 session共享之后直接用HttpSessionListener是不行的
     * 需要注册一个SessionEventHttpSessionListenerAdapter 或者用shiro的SessionListener
     * 本项目直接使用shiro的SessionListener
     * @return
     */
    @Bean
    public SessionListener sessionListener(){
        return new MySessionListener();
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setSessionDAO(sessionDAO());
        List<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(sessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        return sessionManager;
    }

    @Bean
    public SessionValidationScheduler sessionValidationScheduler(){
        QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler();
        scheduler.setSessionValidationInterval(1800000);
        scheduler.setSessionManager(sessionManager());
        return scheduler;
    }

}