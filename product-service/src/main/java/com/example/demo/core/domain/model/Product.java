package com.example.demo.core.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    private String name;
    private String author;
    private String publisher;
    private String description;
    private String imgUrl;
    private int publishYear;
    private int pages;
    private double price;

    public Product(){}

    public Product(String name, String author, String publisher, String description, String imgUrl, int publishYear, int pages, double price) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.imgUrl = imgUrl;
        this.publishYear = publishYear;
        this.pages = pages;
        this.price = price;
    }

    public UUID getId() { return id;}

    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) {this.name = name; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublishYear() { return publishYear; }

    public void setPublishDate(int publishYear) { this.publishYear = publishYear; }

    public int getPages() {return pages; }

    public void setPages(int pages) {this.pages = pages; }

    public void setPrice(double price) { this.price = price; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
