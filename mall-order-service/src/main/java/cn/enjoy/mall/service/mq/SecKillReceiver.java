package cn.enjoy.mall.service.mq;

import cn.enjoy.mall.constant.KillConstants;
import cn.enjoy.mall.service.IOrderService;
import cn.enjoy.mall.vo.KillOrderVo;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类说明：
 */
@Component
public class SecKillReceiver implements ChannelAwareMessageListener {

    @Resource
    private IOrderService orderService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());
            System.out.println("UserReceiver>>>>>>>接收到消息:"+msg);
            try {
                KillOrderVo vo = JSON.parseObject(msg, KillOrderVo.class);

                String kill_order_user = KillConstants.KILL_ORDER_USER+vo.getKillGoodsSpecPriceDetailVo().getId()+vo.getUserId();
                if (null != stringRedisTemplate.opsForValue().get(kill_order_user)){//未超时，则业务处理
                    int orderId = orderService.killOrder(vo);
                    String oldstr = stringRedisTemplate.opsForValue().getAndSet(kill_order_user,String.valueOf(orderId));
                    if (null == oldstr){//已超时，生产端已拒绝
                        orderService.cancel(orderId);
                        stringRedisTemplate.delete(kill_order_user);
                    }
                }

                System.out.println("UserReceiver>>>>>>消息已消费");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);//手工确认，可接下一条
            } catch (Exception e) {
                System.out.println(e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);//失败，则直接忽略此订单

                System.out.println("UserReceiver>>>>>>拒绝消息，直接忽略");
                throw e;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

