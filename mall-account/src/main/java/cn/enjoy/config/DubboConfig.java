package cn.enjoy.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@Configuration
@DubboComponentScan(basePackages = "cn.enjoy.**.service")
public class DubboConfig {

    @Value("${zookeeper.url}")
    private String zookeeperUrl;



    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("mall-account");
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
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(20000);
        consumerConfig.setCheck(false);
        return consumerConfig;
    }

    @Bean
    ReferenceConfig referenceConfig(){
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setUrl("dubbo://127.0.0.1:20880");
        return referenceConfig;
    }
}
