package com.example.messagingredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class MessagingRedisApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingRedisApplication.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch){
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch(){
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = SpringApplication.run(MessagingRedisApplication.class, args);
        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);
        CountDownLatch latch = context.getBean(CountDownLatch.class);

        LOGGER.info("Sending message...");
        for (int i =0; i <= 10; i++){
            template.convertAndSend("chat", "Hello from Redis!");
            Thread.sleep(1000);
        }
        latch.await();
        System.exit(0);

    }

}
