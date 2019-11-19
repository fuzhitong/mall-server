package cn.enjoy;
import cn.enjoy.mall.model.Order;
import cn.enjoy.mall.service.IScheduledService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;


@SpringBootTest(classes= PayServiceApp.class)
@RunWith(SpringRunner.class)
public class ServiceTest {


    @Test
    public  void test1() {
        Order order = new Order();
    }
}
