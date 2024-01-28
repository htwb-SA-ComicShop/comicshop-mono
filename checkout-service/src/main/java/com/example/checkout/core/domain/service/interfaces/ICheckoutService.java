package com.example.checkout.core.domain.service.interfaces;

import com.example.checkout.core.domain.model.Order;
import com.example.checkout.port.user.exception.OrderNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ICheckoutService
{
    Order createOrder (Order order);

    void deleteOrder (UUID id) throws OrderNotFoundException;

    Order getOrder(UUID id) throws OrderNotFoundException;

    List<Order> getAllOrder();

    long getCountOfOrder();
}
