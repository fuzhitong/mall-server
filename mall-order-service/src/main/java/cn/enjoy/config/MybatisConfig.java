package cn.enjoy.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Configuration
public class MybatisConfig {
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Value("${mybatis.config-location}")
    private String configLocation;

    @Resource
    private DataSource myRoutingDataSource;

    /**
     *返回sqlSessionFactory
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(myRoutingDataSource);
        //自定义数据源一定要手工设置mapperLocations， application.yml里设置的不起作用
        sqlSessionFactory.setMapperLocations(applicationContext.getResources(mapperLocations));
        sqlSessionFactory.setConfigLocation(applicationContext.getResource(configLocation));
        return sqlSessionFactory;
    }

}
