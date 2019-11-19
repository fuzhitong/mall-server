package cn.enjoy.users;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户以及授权启动类
 */
@SpringBootApplication
@MapperScan("cn.enjoy.**.dao")
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class);
    }
}
