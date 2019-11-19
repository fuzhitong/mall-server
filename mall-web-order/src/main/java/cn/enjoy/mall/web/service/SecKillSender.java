package cn.enjoy.mall.web.service;

import cn.enjoy.mall.config.RabbitConfig;
import cn.enjoy.mall.vo.KillOrderVo;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明：
 */
@Component
public class SecKillSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(KillOrderVo vo) {
        String msg1 = JSON.toJSONString(vo);
        System.out.println("TopicSender send the 1st : " + msg1);
        this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_SECKILL, RabbitConfig.KEY_SECKILL, msg1);
    }
}
