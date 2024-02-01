package com.example.cart.core.domain.model;

import com.example.cart.port.user.exception.NoPurchaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    private HashMap<UUID, CartItem> cartItems = new HashMap<>();
    ;

    private double totalPrice;
    private LocalDate boughtAt;

    private String linkToInvoice;
    private String linkToContent;
    private String email;

    private boolean isBought = false;

    public Cart() {
    }

    public String getUsername() {
        return username;
    }

    public void setCartItems(HashMap<UUID, CartItem> cartItems) {
        this.cartItems = cartItems;
        this.totalPrice = calculateTotalPrice();
    }

    public HashMap<UUID, CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double calculateTotalPrice() {
        return this.cartItems
                .values()
                .stream()
                .map(CartItem::getPrice)
                .reduce(0.0, Double::sum);
    }

    public String generateInvoice() {
        //TODO generate a file, safe it and sent it to stripe
        if (isBought) {
            StringBuilder invoiceBuilder = new StringBuilder();

            // Invoice Header
            invoiceBuilder.append("Invoice for Online Comic Book Store\n");
            invoiceBuilder.append("Date: ").append(boughtAt).append("\n");
            invoiceBuilder.append("Username: ").append(username).append("\n");
            invoiceBuilder.append("\n");

            // Comic Details
            invoiceBuilder.append("Comics Purchased:\n");
            for (Map.Entry<UUID, CartItem> entry : cartItems.entrySet()) {
                String comicId = entry.getKey().toString();
                String comicTitle = entry.getValue().getName();
                double comicPrice = entry.getValue().getPrice();
                invoiceBuilder.append("- Comic ID: ").append(comicId)
                        .append(", Title: ").append(comicTitle)
                        .append(", Price: $").append(comicPrice)
                        .append("\n");
            }
            invoiceBuilder.append("\n");

            // Total Price
            invoiceBuilder.append("Total Price: $").append(totalPrice).append("\n");

            //Save into file
            Path filePath = Path.of("cart-service/files/invoice.txt");
            try {
                Files.write(filePath, invoiceBuilder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return filePath.toString();
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

    public String getLinkToInvoice() {
        return linkToInvoice;
    }

    public void setLinkToInvoice(String linkToInvoice) {
        this.linkToInvoice = linkToInvoice;
    }

    public String getLinkToContent() {
        return linkToContent;
    }

    public void setLinkToContent(String linkToContent) {
        this.linkToContent = linkToContent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
