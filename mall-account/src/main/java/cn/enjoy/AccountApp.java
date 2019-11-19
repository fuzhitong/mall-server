package cn.enjoy;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@SpringBootApplication
@Import(FdfsClientConfig.class)
public class AccountApp {

    public static void main(String[] args) {
         SpringApplication.run(AccountApp.class, args);
    }
}
