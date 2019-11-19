package cn.enjoy;
import cn.enjoy.mall.service.IScheduledService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest(classes=OrderServiceApp.class)
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Resource
    private IScheduledService iScheduledService;

    @Test
    public  void test1() {
        iScheduledService.scheduledHotProduct();
    }
}
