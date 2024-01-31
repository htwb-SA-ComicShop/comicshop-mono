package com.example.cart.core.domain.service.interfaces;

import com.example.cart.core.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICartRepository extends JpaRepository<Cart, UUID> {}


