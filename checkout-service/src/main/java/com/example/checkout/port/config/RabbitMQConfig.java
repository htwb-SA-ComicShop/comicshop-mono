package com.example.checkout.port.config;

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

    @Value("product")
    private String queue;

    @Value("cart_item")
    private String cartQueue;

    @Value("product_routing_key")
    private String routingKey;

    @Value("cart_item_routing_key")
    private String cartItemRoutingKey;

    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    @Bean
    public Queue cartQueue(){
        return new Queue(cartQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding itemBinding(){
        return BindingBuilder
                .bind(cartQueue())
                .to(exchange())
                .with(cartItemRoutingKey);
    }

}