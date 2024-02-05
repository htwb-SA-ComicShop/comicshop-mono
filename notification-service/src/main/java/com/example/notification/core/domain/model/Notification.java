package com.example.notification.core.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "message")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @JsonProperty
    private String orderId;
    @JsonProperty
    private String recipient;
    @JsonProperty
    private String linkToContent;
    @JsonProperty
    private String linkToInvoice;
    private String bodyText;
    private Date timeStamp;
    private  String subject;

    private KindOfNotification kind;

    public Notification() {
    }

    public Notification(String recipient, KindOfNotification kind) {
        this.recipient = recipient;
        this.kind = kind;
        this.bodyText = crateBodyText();
        this.subject = crateSubject();
    }

    public Notification(String recipient, String orderId, String linkToContent, String linkToInvoice, KindOfNotification kind) {
        this.recipient = recipient;
        this.linkToContent = linkToContent;
        this.orderId = orderId;
        this.kind = kind;
        this.bodyText = crateBodyText();
        this.subject = crateSubject();
        this.linkToInvoice = linkToInvoice;
    }

    //TODO do we need two constructors here??
    public Notification(String recipient, String orderId, String linkToContent, String linkToInvoice) {
        this.recipient = recipient;
        this.linkToContent = linkToContent;
        this.orderId = orderId;
        this.bodyText = crateBodyText();
        this.subject = crateSubject();
        this.linkToInvoice = linkToInvoice;
    }

    private String crateBodyText() {
        return switch (kind) {
            case ORDER -> orderText();
            case WELCOME -> welcomeText();
        };
    }
    private String crateSubject() {
        return switch (kind) {
            case ORDER -> orderSubject();
            case WELCOME -> welcomeSubject();
        };
    }

    private String welcomeSubject() {
        return "Welcome to the ComicShop";
    }

    private String orderSubject() {
        return "Here is your Order";
    }

    private String welcomeText() {
        StringBuilder welcomeLetter = new StringBuilder();

        welcomeLetter.append("Dear ").append(recipient).append(",\n\n")
                .append("Welcome to our Online Comic Shop!\n\n")
                .append("We're thrilled to have you as a member of our community. ")
                .append("Get ready to dive into a world of superheroes, adventures, and more!\n\n")
                .append("As a registered member, you can explore our extensive collection of comics, ")
                .append("manage your preferences, and make your orders even faster with the Quickcheckout option.\n\n")
                .append("If you have any questions or need assistance, don't hesitate to reach out to us.\n\n")
                .append("Happy reading!\n\n")
                .append("Best regards,\nyour    ComicShop Team");

        return welcomeLetter.toString();
    }

    private String orderText() {
        StringBuilder messageBody = new StringBuilder();

        messageBody.append("Dear ").append(recipient).append(",\n\n")
                .append("Thank you for your order (Order ID: ").append(orderId).append("), ")
                .append("placed at ").append(timeStamp).append(". \n")
                .append("You can download your purchased items using the following link:\n")
                .append(linkToContent).append("\n\n")
                .append("You can download your invoice here:\n")
                .append(linkToInvoice).append("\n\n")
                .append("If you have any questions or need further assistance, feel free to contact us.\n\n")
                .append("Best regards,\nYour ComicShop");

        return messageBody.toString();

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getLinkToContent() {
        return linkToContent;
    }

    public void setLinkToContent(String downloadLink) {
        this.linkToContent = downloadLink;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getSubject() {
        return subject;
    }

    public void setKind(KindOfNotification kind){
        this.kind = kind;
    }
}
