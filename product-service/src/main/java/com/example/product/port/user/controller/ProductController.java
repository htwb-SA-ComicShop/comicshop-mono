package com.example.product.port.user.controller;

import com.example.product.core.domain.model.AddToCartDTO;
import com.example.product.core.domain.model.Product;
import com.example.product.core.domain.service.interfaces.IProductService;
import com.example.product.port.shoppingcart.producer.ProductToCartProducer;
import com.example.product.port.user.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductToCartProducer productToCartProducer;
    @Autowired
    private IProductService productService;

    /**
     * Creates a new product and saves it to the database.
     *
     * @param product The product to be saved.
     */
    @PostMapping(path = "/add-product")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Product product) {
        Product newProduct = new Product(
                product.getName(),
                product.getAuthor(),
                product.getPublisher(),
                product.getDescription(),
                product.getImgUrl(),
                product.getPublishYear(),
                product.getPages(),
                product.getPrice()
        );
        productService.createProduct(newProduct);
    }

    /**
     * Updates an existing product.
     *
     * @param product The updated product details.
     * @param id      The unique identifier of the product to be updated.
     */
    @PutMapping(path = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public @ResponseBody void update(@RequestBody Product product, @PathVariable UUID id) {
        System.out.println("Updating product: " + product);
        productService.updateProduct(product, id);
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id The unique identifier of the product.
     * @return The retrieved product.
     * @throws ProductNotFoundException If the product with the given ID is not found.
     */
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable UUID id) {
        Product product = productService.getProduct(id);
        if (product == null) throw new ProductNotFoundException(id);
        return product;
    }

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id The unique identifier of the product to be deleted.
     */
    @DeleteMapping(path = "/product/{id}")
    @CrossOrigin(origins = "*")
    public @ResponseBody void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    /**
     * Retrieves a list of all products.
     *
     * @return List of products.
     */
    @GetMapping("/products")
    public @ResponseBody List<Product> getProducts() {
        return productService.getAllProducts();
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param product The product to be added to the cart.
     */
    @PostMapping(path = "/add-to-cart")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void addToCart(@RequestBody Product product) {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> tokenAttributes = authToken.getTokenAttributes();
        String userName = (String) tokenAttributes.get("preferred_username");
        String email = (String) tokenAttributes.get("email");
        //TODO if tokenAttributes.replace() then does it replace the value in Keycloak??
        System.out.println("Adding to cart: " + product.getName());
        System.out.println("userName: " + userName);
        System.out.println("email: " + email);
        AddToCartDTO cartItem = new AddToCartDTO(userName, email, product);
        productToCartProducer.sendToCart(cartItem);
    }
}
