package cn.enjoy;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@SpringBootApplication
@EnableDubbo
public class PayServiceApp {
    public static void main(String[] args) {
         SpringApplication.run(PayServiceApp.class, args);
    }
}
