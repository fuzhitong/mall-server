package cn.enjoy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@SpringBootApplication
@MapperScan("cn.enjoy.mall.dao")
@EnableScheduling
public class OrderServiceApp {

    public static void main(String[] args) {
         SpringApplication.run(OrderServiceApp.class, args);
    }
}
