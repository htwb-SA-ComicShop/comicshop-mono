package com.example.demo.core.domain.service.impl;

import com.example.demo.core.domain.service.interfaces.ICartRepository;
import com.example.demo.core.domain.service.interfaces.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CartService implements ICartService {
    @Autowired
    private final ICartRepository cartRepository;
    // TODO: Implement CartService methods
}
