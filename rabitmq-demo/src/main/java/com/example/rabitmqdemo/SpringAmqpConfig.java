package com.example.rabitmqdemo;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class SpringAmqpConfig {

    public final static String queueName = "com.baeldung.spring-amqp-simple.queue";
    public final static String exchangeName = "com.baeldung.spring-amqp-simple.exchange";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Exchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer springAmqpContainer(ConnectionFactory connectionFactory,
                                                       MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageConsumer messageReceiver) {
        return new MessageListenerAdapter(messageReceiver, "receiveMessage");
    }

}