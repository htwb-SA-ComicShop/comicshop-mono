package com.example.demo.core.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class AddToCartDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4242L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private UUID productId;
    private String productName;
    private String author;
    private String imgUrl;
    private double price;

    public AddToCartDTO(String username, String email, Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.author = product.getAuthor();
        this.imgUrl = product.getImgUrl();
        this.price = product.getPrice();
        this.username = username;
        this.email = email;
    }

    public UUID getId() { return id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getAuthor() {
        return author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public double getPrice() {
        return price;
    }
}
