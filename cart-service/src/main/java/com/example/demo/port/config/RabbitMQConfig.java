package com.example.demo.port.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("product_exchange")
    private String exchange;

    @Value("cart_item")
    private String cartQueue;

    @Value("cart_item_routing_key")
    private String cartItemRoutingKey;

    @Bean
    public Queue cartQueue(){
        return new Queue(cartQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding itemBinding(){
        return BindingBuilder
                .bind(cartQueue())
                .to(exchange())
                .with(cartItemRoutingKey);
    }

}