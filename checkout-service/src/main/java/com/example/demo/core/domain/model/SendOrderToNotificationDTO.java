package com.example.demo.core.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class SendOrderToNotificationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4242L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String linkToInvoice;

    private String linkToContent;
    private String recipient;


    public SendOrderToNotificationDTO(String linkToContent, String linkToInvoice, String recipient) {
        this.linkToContent = linkToContent;
        this.linkToInvoice = linkToInvoice;
        this.recipient = recipient;
    }

    public UUID getId() { return id;}

    public void setLinkToContent(String linkToContent) {
        this.linkToContent = linkToContent;
    }

    public String getLinkToContent() {
        return linkToContent;
    }

    public String getLinkToInvoice() {
        return linkToInvoice;
    }

    public void setLinkToInvoice(String linkToInvoice) {
        this.linkToInvoice = linkToInvoice;
    }
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
