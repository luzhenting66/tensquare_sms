package cn.pipilu.tensquare.sms.config;

import cn.pipilu.tensquare.sms.listener.SmsListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Value("${spring.rabbitmq.sms.queue.name}")
    private String smsQueueName;
    @Autowired
    private SmsListener smsListener;
    /**
     * 单一消费者
     * @return
     */
//    @Bean(name = "singleListenerContainer")
//    public SimpleRabbitListenerContainerFactory listenerContainer(){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setConcurrentConsumers(1);
//        factory.setMaxConcurrentConsumers(1);
//        factory.setPrefetchCount(1);
//        factory.setTxSize(1);
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        return factory;
//    }
    //============userOrder===消费者确认==========
    @Bean
    public SimpleMessageListenerContainer listenerContainerUserOrder(@Qualifier(value = "smsQueue") Queue smsQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        //container.setMessageConverter(new Jackson2JsonMessageConverter());
        //消息确认机制
        container.setQueues(smsQueue);
        container.setMessageListener(smsListener);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认
        return container;
    }
    @Bean(name = "smsQueue")
    public Queue smsQueue(){
        return new Queue(smsQueueName,true);
    }
}
