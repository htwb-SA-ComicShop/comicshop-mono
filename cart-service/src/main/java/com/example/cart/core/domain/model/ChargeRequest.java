package com.example.cart.core.domain.model;

import lombok.Data;

/*
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////!!DUE TO CONSTRUCTION WORK THIS SECTION IS UNAVAILABLE!!///////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 */
@Data
public class ChargeRequest {

    public enum Currency {
         USD;
    }
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
}