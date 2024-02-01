package com.example.cart.core.domain.model;

import com.example.cart.port.user.exception.NoPurchaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    private String username;

    @OneToMany
    private List<CartItem> cartItems = new LinkedList<>();;

    private double totalPrice = 0.0;
    private LocalDate boughtAt;

    private boolean isBought = false;

    public Cart() {}

    public String getUsername() {
        return username;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.totalPrice = calculateTotalPrice();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double calculateTotalPrice(){
        return this.cartItems
                .stream()
                .map(CartItem::getPrice)
                .reduce(0.0, Double::sum);
    }

    public String generateInvoice() {
        //TODO generate a file, safe it and sent it to stripe
        if(isBought) {
            StringBuilder invoiceBuilder = new StringBuilder();

            // Invoice Header
            invoiceBuilder.append("Invoice for Online Comic Book Store\n");
            invoiceBuilder.append("Date: ").append(boughtAt).append("\n");
            invoiceBuilder.append("Username: ").append(username).append("\n");
            invoiceBuilder.append("\n");

            // Comic Details
            invoiceBuilder.append("Comics Purchased:\n");
            cartItems.forEach(entry -> {
                String comicId = entry.getId().toString();
                String comicTitle = entry.getName();
                double comicPrice = entry.getPrice();
                invoiceBuilder.append("- Comic ID: ").append(comicId)
                        .append(", Title: ").append(comicTitle)
                        .append(", Price: $").append(comicPrice)
                        .append("\n");
            });
            invoiceBuilder.append("\n");

            // Total Price
            invoiceBuilder.append("Total Price: $").append(totalPrice).append("\n");

            return invoiceBuilder.toString();
        }
        throw new NoPurchaseException();
    }

    public void setBoughtAt(LocalDate boughtAt) {
        this.boughtAt = boughtAt;
    }

    public LocalDate getBoughtAt() {
        return boughtAt;
    }

    public UUID getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public boolean isBought() {
        return isBought;
    }
}
