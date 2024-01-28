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

    /*
    @Value("cart_exchange")
    private String exchange;


    @Value("cart")
    private String queue;

    @Value("orderInfo")
    private String orderQueue;

    @Value("cart_routing_key")
    private String routingKey;

    @Value("order_info_routing_key")
    private String orderInfoRoutingKey;
     */

    @Value("order_exchange")
    private String exchange;

    @Value("orderInfo")
    private String queue;

    @Value("order_info_routing_key")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    /*
    @Bean
    public Queue cartQueue() {
        return new Queue(orderQueue);
    }
     */

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    /*
    @Bean
    public Binding itemBinding() {
        return BindingBuilder
                .bind(cartQueue())
                .to(exchange())
                .with(orderInfoRoutingKey);
    }

     */

}