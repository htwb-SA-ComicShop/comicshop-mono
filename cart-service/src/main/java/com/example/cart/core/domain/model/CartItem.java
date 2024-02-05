package com.example.cart.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class CartItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "productId")
    private UUID productId;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private double price;

    @Column(name = "linkToProduct")
    private String linkToProduct;

    @Column(name = "imgUrl")
    private String imgUrl;

    public CartItem(UUID productId, String name, String author, double price, String imgUrl, String linkToProduct) {
        this.productId = productId;
        this.name = name;
        this.author = author;
        this.price = price;
        this.imgUrl = imgUrl;
        this.linkToProduct = linkToProduct;
    }

    public CartItem() {
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getLinkToProduct() {
        return linkToProduct;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }
}
