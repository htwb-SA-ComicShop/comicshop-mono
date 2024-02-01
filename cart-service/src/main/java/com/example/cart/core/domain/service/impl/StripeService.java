package com.example.cart.core.domain.service.impl;

import com.example.cart.core.domain.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.FileLink;
import com.stripe.param.FileLinkCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("sk_test_51Oe0nyBxAbUutujzVap9Dr58oHZmOT8cMxDEuuSsLvTispvwX5Co9fDhxLwddexlNK9lOQeCVxIKmUWknVeyCYWQ00OQ7xB7oT")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge charge(ChargeRequest chargeRequest)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }

    public FileLink getLinkToInvoice(String location) throws StripeException {
        FileLinkCreateParams params =
                FileLinkCreateParams.builder().setFile(location).build();
        return FileLink.create(params);
    }
}
