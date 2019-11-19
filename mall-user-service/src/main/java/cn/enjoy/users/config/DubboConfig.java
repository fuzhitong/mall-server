package cn.enjoy.users.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@Configuration
@DubboComponentScan(basePackages = "cn.enjoy.**.service.impl")
public class DubboConfig {

    @Value("${zookeeper.url}")
    private String zookeeperUrl;


    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("mall-user-service");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zookeeperUrl);
        registryConfig.setClient("zkclient");
        return registryConfig;
    }



    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(20882);
        return protocolConfig;
    }

}
