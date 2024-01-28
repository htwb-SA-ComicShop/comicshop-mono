package com.example.demo;

import com.example.demo.core.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestH2Repository extends JpaRepository<Product, UUID> {}
