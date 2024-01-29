package com.example.notification.port.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("orderInfo")
    private String orderQueue;

    @Value("cart_exchange")
    private String exchange;

    @Value("order_info_routing_key")
    private String orderInfoRoutingKey;

    @Bean
    public Queue itemQueue(){
        return new Queue(orderQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding itemBinding(){
        return BindingBuilder
                .bind(itemQueue())
                .to(exchange())
                .with(orderInfoRoutingKey);
    }

}