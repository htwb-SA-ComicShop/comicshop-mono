package com.example.demo.core.domain.service.interfaces;

import com.example.demo.core.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICheckoutRepository extends JpaRepository<Order, UUID> {}


