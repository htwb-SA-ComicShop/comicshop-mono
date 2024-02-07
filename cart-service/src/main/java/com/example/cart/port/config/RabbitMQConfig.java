package com.example.cart.port.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("cart_exchange")
    private String exchange;

    @Value("cart")
    private String cartQueue;

    @Value("cart_item_routing_key")
    private String cartRoutingKey;

    @Value("orderInfo")
    private String orderQue;

    @Value("order_info_routing_key")
    private String orderRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(orderQue);
    }

    @Bean
    public Queue cartQueue() {
        return new Queue(cartQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(orderRoutingKey);
    }


    @Bean
    public Binding itemBinding() {
        return BindingBuilder.bind(cartQueue()).to(exchange()).with(cartRoutingKey);
    }


}