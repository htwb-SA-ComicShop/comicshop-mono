package com.example.cart.core.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @Column(name = "id")
    private String username;
    // FIXME: Add real cart model
    private int test;

    public Cart() {
    }

    public String getUsername() {
        return username;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
}
