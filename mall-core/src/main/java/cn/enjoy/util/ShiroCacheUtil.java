package cn.enjoy.util;

import cn.enjoy.mall.constant.RedisCacheNames;
import cn.enjoy.mall.constant.SsoConstants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2018/3/29.
 */
public class ShiroCacheUtil {

    private Cache<String, Object> cache;
    @Resource
    private CacheManager cacheManager;


    @PostConstruct
    public void init(){
        this.cache = cacheManager.getCache(RedisCacheNames.CODE_CACHE);
    }


    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }


    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }


    public void removeAccessToken(String accessToken) {
        cache.remove(accessToken);
    }

    /**
     * 获取到用户名之后code就不用了，扔掉
     * @param authCode
     * @return
     */
    public String getUsernameByAuthCode(String authCode) {
        String userName = (String)cache.get(authCode);
        cache.remove(authCode);
        return userName;
    }


    public String getUsernameByAccessToken(String accessToken) {
        return (String)cache.get(accessToken);
    }

    /**
     * 检查code是否合法
     * @param authCode
     * @return
     */
    public boolean checkAuthCode(String authCode) {
        return cache.get(authCode) != null;
    }


    public boolean checkAccessToken(String accessToken) {
        return cache.get(accessToken) != null;
    }


    public void put(String key, String value){
        cache.put(key, value);
    }

    public void removeKey(String key){
        cache.remove(key);
    }

    public void putUser(String userName, String value){
        cache.put(SsoConstants.SSO_USERS + ":" + userName, value);
    }

    public String getUser(String userName){
        return (String )cache.get(SsoConstants.SSO_USERS + ":" + userName);
    }

    public void removeUser(String userName){
        cache.remove(SsoConstants.SSO_USERS + ":" + userName);
    }

    public long getExpireIn() {
        return 3600L;
    }
}
