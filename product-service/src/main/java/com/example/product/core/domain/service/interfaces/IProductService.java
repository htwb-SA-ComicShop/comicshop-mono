package com.example.product.core.domain.service.interfaces;

import com.example.product.core.domain.model.Product;
import com.example.product.port.user.exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;
/**
 * Service interface for managing products.
 */
public interface IProductService {

    /**
     * Creates a new product.
     *
     * @param product The product to be created.
     * @return The created product.
     */
    Product createProduct(Product product);

    /**
     * Updates an existing product.
     *
     * @param product The updated product details.
     * @param id      The unique identifier of the product to be updated.
     * @return The updated product.
     * @throws ProductNotFoundException If the product with the given ID is not found.
     */
    Product updateProduct(Product product, UUID id) throws ProductNotFoundException;

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id The unique identifier of the product to be deleted.
     * @throws ProductNotFoundException If the product with the given ID is not found.
     */
    void deleteProduct(UUID id) throws ProductNotFoundException;

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id The unique identifier of the product.
     * @return The retrieved product.
     * @throws ProductNotFoundException If the product with the given ID is not found.
     */
    Product getProduct(UUID id) throws ProductNotFoundException;

    /**
     * Retrieves a list of all products.
     *
     * @return List of products.
     */
    List<Product> getAllProducts();

    /**
     * Retrieves the count of all products.
     *
     * @return The count of products.
     */
    long getCountOfProducts();
}
