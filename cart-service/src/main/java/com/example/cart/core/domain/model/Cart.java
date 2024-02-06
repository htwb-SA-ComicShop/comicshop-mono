package com.example.cart.core.domain.model;

import com.example.cart.port.user.exception.NoPurchaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    @Column(name = "username")
    private String username;

    @Column(name = "cartItems")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new LinkedList<>();

    @Column(name = "totalPrice")
    private double totalPrice = 0.0;

    @Column(name = "boughtAt")
    private LocalDate boughtAt;
    @Column(name = "email")
    private String email;

    private String linkToInvoice;
    private String linkToContent;



    @Column(name = "isBought")
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
       if (this.cartItems.size()>=1) {
           return this.cartItems
                   .stream()
                   .map(CartItem::getPrice)
                   .reduce(0.0, Double::sum);
       }
       return 0;
    }

    public String generateInvoice() {
        if (isBought) {
            StringBuilder invoiceBuilder = new StringBuilder();

            // Invoice Header
            invoiceBuilder.append("Invoice for Online Comic Book Store\n");
            invoiceBuilder.append("Date: ").append(boughtAt).append("\n");
            invoiceBuilder.append("Username: ").append(username).append("\n");
            invoiceBuilder.append("\n");

            // Comic Details
            invoiceBuilder.append("Comics Purchased:\n");
            cartItems.forEach(entry -> {
                String comicId = entry.getProductId().toString();
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

            //Save into file
           return writeFIle(invoiceBuilder.toString(), "Invoice");
        }
        throw new NoPurchaseException();
    }

    //TODO get the comic from the db
    public String getPathToComic(String comicName) {
        if (isBought) {
            return writeFIle(comicName, comicName);
        }
        throw new NoPurchaseException();
    }

    private String writeFIle(String content, String name){
        Path path = Paths.get( name + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
            writer.write(content);
            writer.close();

            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setBoughtAt(LocalDate boughtAt) {
        this.boughtAt = boughtAt;
        this.isBought = true;
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
