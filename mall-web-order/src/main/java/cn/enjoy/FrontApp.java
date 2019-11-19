package cn.enjoy;

import cn.enjoy.config.ShiroOauth2Configuration;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Ray
 * @date 2018/2/1.
 */
@SpringBootApplication
@Import({ShiroOauth2Configuration.class, FdfsClientConfig.class})
public class FrontApp {

    public static void main(String[] args) {
         SpringApplication.run(FrontApp.class, args);
    }

}
