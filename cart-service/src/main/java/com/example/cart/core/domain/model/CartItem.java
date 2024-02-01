package com.example.cart.core.domain.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CartItem {

    //TODO are these tags needed
    @Id
    @Column(name = "id")
    private UUID id;
    private String name;
    private String author;
    private double price;
    private String linkToProduct;

    private String imgUrl;

    public CartItem(UUID id, String name, String author, double price, String imgUrl, String linkToProduct) {
        this.id = id;
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
}
