package com.example.product.core.domain.service.interfaces;

import com.example.product.core.domain.model.Product;
import com.example.product.port.user.exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IProductService
{
    Product createProduct (Product product);

    Product updateProduct (Product product, UUID id) throws ProductNotFoundException;

    void deleteProduct (UUID id) throws ProductNotFoundException;

    Product getProduct(UUID id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    long getCountOfProducts();
}
