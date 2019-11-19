package cn.enjoy.config;

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
@DubboComponentScan(basePackages = "cn.enjoy.mall.service.impl")
public class DubboConfig {
    @Value("${zookeeper.url}")
    private String zookeeperUrl;
    @Value("${dubbo.name}")
    private String appName;
    @Value("${dubbo.port}")
    private int port;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(appName);
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
        protocolConfig.setPort(port);
//        protocolConfig.setHost("192.168.0.119");
        return protocolConfig;
    }

}
