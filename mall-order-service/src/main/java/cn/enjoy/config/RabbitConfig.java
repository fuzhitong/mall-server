package cn.enjoy.config;

import cn.enjoy.mall.service.mq.SecKillReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com
 *类说明：
 */
@Configuration
public class RabbitConfig {

    public final static String EXCHANGE_SECKILL = "order.seckill.producer";
    public final static String KEY_SECKILL = "order.seckill";
    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueSecKillMessage() {
        return new Queue("order.seckill.producer");
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_SECKILL);
    }

    @Bean
    public Binding bindingSecKillExchangeMessage() {
        return BindingBuilder
                .bind(queueSecKillMessage())
                .to(exchange())
                .with(KEY_SECKILL);
    }


    //===============消费者确认==========
    @Bean
    public SimpleMessageListenerContainer messageContainer(SecKillReceiver secKillReceiver) {
        SimpleMessageListenerContainer container
                = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queueSecKillMessage());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(secKillReceiver);
        return container;
    }


}
