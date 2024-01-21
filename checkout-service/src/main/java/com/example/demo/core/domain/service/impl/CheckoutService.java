package com.example.demo.core.domain.service.impl;

import com.example.demo.core.domain.model.Order;
import com.example.demo.core.domain.service.interfaces.ICheckoutRepository;
import com.example.demo.core.domain.service.interfaces.ICheckoutService;
import com.example.demo.port.user.exception.ProductNotFoundException;
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
    private final ICheckoutRepository productRepository;

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(UUID id) throws ProductNotFoundException {

    }

    @Override
    public Order getOrder(UUID id) throws ProductNotFoundException {
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
