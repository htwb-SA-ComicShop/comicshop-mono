package com.example.cart.port.checkout.controller;

import com.example.cart.core.domain.model.ChargeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////!!DUE TO CONSTRUCTION WORK THIS SECTION IS UNAVAILABLE!!///////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 */
@Controller
public class CheckoutController {

    @Value("pk_test_51Oe0nyBxAbUutujzcEFX080Y4tuRGwpGoPfhtZHmRScwvANKtun2cmbB6UM0lPkQbL0d9fT9yO3gBttsYtBi0mpi00JQteIE2v")
    private String stripePublicKey;

    @RequestMapping("/checkout")
    public String checkout( Model model) {
        model.addAttribute("amount", 50 * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "checkout";
    }
}
