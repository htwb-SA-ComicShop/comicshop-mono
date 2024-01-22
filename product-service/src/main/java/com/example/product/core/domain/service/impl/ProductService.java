package com.example.product.core.domain.service.impl;

import com.example.product.core.domain.model.Product;
import com.example.product.core.domain.service.interfaces.IProductRepository;
import com.example.product.core.domain.service.interfaces.IProductService;
import com.example.product.port.user.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ProductService implements IProductService {
    @Autowired
    private final IProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, UUID id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setAuthor(product.getAuthor());
        existingProduct.setPages(product.getPages());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImgUrl(product.getImgUrl());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID is invalid.");
        }
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public long getCountOfProducts() {
        return productRepository.count();
    }
}
