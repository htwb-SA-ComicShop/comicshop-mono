package com.example.cart.core.domain.service.impl;

import com.example.cart.core.domain.model.Order;
import com.example.cart.core.domain.service.interfaces.ICartRepository;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.example.cart.core.domain.service.interfaces.ICheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CheckoutService implements ICheckoutService {
    @Autowired
    private final ICartRepository productRepository;

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(UUID id) throws CartNotFoundException {

    }

    @Override
    public Order getOrder(UUID id) throws CartNotFoundException {
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        return null;
    }

    @Override
    public long getCountOfOrder() {
        return 0;
    }
}
