package cn.enjoy.sys.security;

import cn.enjoy.core.utils.HttpUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.constant.RedisKey;
import cn.enjoy.sys.model.Oauth2Client;
import cn.enjoy.sys.service.IAuthorizeService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 主要用于通知各子系统Logout
 * @author Ray
 * @date 2018/3/30.
 */
public class MySessionListener implements SessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Reference
    private IAuthorizeService authorizeService;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*private <T> T getObjectFromApplication(ServletContext servletContext, Class<T> requiredType){
        //通过WebApplicationContextUtils 得到Spring容器的实例。
        ApplicationContext application= WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //返回Bean的实例。
        return application.getBean(requiredType);
    }*/

    @Override
    public void onStart(Session session) {

        logger.info("Session {} 被创建", session.getId());
    }

    @Override
    public void onStop(Session session) {
        logger.info("Session 被销毁");
        //通过HttpClient通知各子系统Logout
        Set keys = redisTemplate.opsForHash().keys(RedisKey.CLIENT_SESSIONS);
        Map<String , Oauth2Client> clientMap = authorizeService.getClientMap();
        if(keys != null){
            for(Object key : keys){
                String clientSessionId = (String) key;
                String clientId = (String) redisTemplate.opsForHash().get(RedisKey.CLIENT_SESSIONS, clientSessionId);
                //发通知给子系统根据sessionId来注销
                logger.info("通知子系统 [{}] 注销用户 [{}]， URL [{}]", clientId, clientSessionId, clientMap.get(clientId).getLogoutUrl());
                HttpUtil.post(clientMap.get(clientId).getLogoutUrl()+ clientSessionId, null);
            }
        }
        //删除缓存的子系统session信息
        redisTemplate.delete(RedisKey.CLIENT_SESSIONS);
    }

    @Override
    public void onExpiration(Session session) {
        //session失效暂不作处理
    }
}
