package com.example.checkout.core.domain.model;


import com.example.product.core.domain.model.AddToCartDTO;
import com.example.product.core.domain.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.ArrayList;
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
    //TODO Rename because its should be a cart not a product
    @ElementCollection
    private ArrayList<AddToCartDTO> content;
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
