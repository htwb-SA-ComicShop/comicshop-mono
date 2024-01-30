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

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "description")
    private String description;

    @Column(name = "imgurl")
    private String imgUrl;

    @Column(name = "publishyear")
    private Integer publishYear;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "price")
    private Double price;

    public Product(){}

    public Product(String name, String author, String publisher, String description, String imgUrl, Integer publishYear, Integer pages, Double price) {
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

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getPublishYear() { return publishYear; }

    public void setPublishDate(Integer publishYear) { this.publishYear = publishYear; }

    public Integer getPages() {return pages; }

    public void setPages(Integer pages) {this.pages = pages; }

    public void setPrice(Double price) { this.price = price; }

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
