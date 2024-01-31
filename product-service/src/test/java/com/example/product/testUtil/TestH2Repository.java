package com.example.product.testUtil;

import com.example.product.core.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestH2Repository extends JpaRepository<Product, UUID> {}
