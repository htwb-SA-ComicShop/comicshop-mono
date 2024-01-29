package com.example.cart.core.domain.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;


    //TODO image

    private  String name;
    private  String author;
    private  double price;
    private  String linkToProduct;

    public CartItem(String name, String author, double price, String linkToProduct) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.linkToProduct = linkToProduct;
    }

    public CartItem() {}

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

    public CartItem safeInDb(){
        //TODO safe in db to get UUID
        return null;
    }
}
