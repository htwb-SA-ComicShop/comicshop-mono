package com.example.product.core.domain.service.interfaces;

import com.example.product.core.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for persisting product data
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
}


