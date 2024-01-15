package com.example.demo.core.domain.service.interfaces;

import com.example.demo.core.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String> {}


