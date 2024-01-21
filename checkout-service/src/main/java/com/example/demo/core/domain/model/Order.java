package com.example.demo.core.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    private double price;
    //TODO
    //private ArrayList<Products> content;
    private Date date;
    public Order(){}

    public Order(double price, Date date ) {
        this.price = price;
        this.date = date;

        //this.content=content;
    }

    public UUID getId() { return id;}

    public void setId(UUID id) { this.id = id; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public void setPrice(double price) { this.price = price; }
}
