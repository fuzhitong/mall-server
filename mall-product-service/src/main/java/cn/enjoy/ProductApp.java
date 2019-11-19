package cn.enjoy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@SpringBootApplication
@MapperScan("cn.enjoy.**.dao")
public class ProductApp {

    public static void main(String[] args) {
         SpringApplication.run(ProductApp.class, args);
    }
}
