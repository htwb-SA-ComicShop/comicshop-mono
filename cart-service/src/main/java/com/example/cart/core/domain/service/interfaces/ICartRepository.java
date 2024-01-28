package com.example.cart.core.domain.service.interfaces;

import com.example.cart.core.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String> {}


