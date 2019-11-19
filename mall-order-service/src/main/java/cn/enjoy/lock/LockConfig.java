package cn.enjoy.lock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

    @Value("${zookeeper.url}")
    private String zookeeperUrl;

    @Bean
    public Lock zkLock(){
        return  new ZookeeperDistrbuteLock(zookeeperUrl);
    }

}
