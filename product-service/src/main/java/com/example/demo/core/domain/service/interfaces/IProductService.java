package com.example.demo.core.domain.service.interfaces;

import com.example.demo.core.domain.model.Product;
import com.example.demo.port.user.exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IProductService
{
    Product createProduct (Product product);

    Product updateProduct (Product product) throws ProductNotFoundException;

    void deleteProduct (UUID id) throws ProductNotFoundException;

    Product getProduct(UUID id) throws ProductNotFoundException;

    List<Product> getAllProducts();
}
