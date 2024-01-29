package com.example.cart.core.domain.service.interfaces;

import com.example.cart.core.domain.model.Order;
import com.example.cart.port.user.exception.CartNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ICheckoutService
{
    Order createOrder (Order order);

    void deleteOrder (UUID id) throws CartNotFoundException;

    Order getOrder(UUID id) throws CartNotFoundException;

    List<Order> getAllOrder();

    long getCountOfOrder();
}
