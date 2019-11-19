package cn.enjoy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Ray
 * @date 2018/3/22.
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
