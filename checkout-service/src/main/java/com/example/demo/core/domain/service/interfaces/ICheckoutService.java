package com.example.demo.core.domain.service.interfaces;

import com.example.demo.core.domain.model.Order;
import com.example.demo.port.user.exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ICheckoutService
{
    Order createOrder (Order order);

    void deleteOrder (UUID id) throws ProductNotFoundException;

    Order getOrder(UUID id) throws ProductNotFoundException;

    List<Order> getAllOrder();

    long getCountOfOrder();
}
