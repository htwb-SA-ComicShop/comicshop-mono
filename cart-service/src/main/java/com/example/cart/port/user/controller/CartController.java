package com.example.cart.port.user.controller;

import com.example.cart.core.domain.service.interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    // TODO: Implement CRUD endpoints for CartController

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/keycloak/userCartId/{user}")
    @CrossOrigin(origins = "*")
    public String getCartIdFromKeykloak(@PathVariable String user) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.getUserCartId(user);
    }

    @GetMapping("/keycloak/updateCartId/{userName}/{newCartId}")
    @CrossOrigin(origins = "*")
    public String updateKeycloakCartId(@PathVariable String userName, @PathVariable String newCartId) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.updateCartId(userName, newCartId);
    }

    @GetMapping("/keycloak/resetPassword/{username}/{newPassword}")
    @CrossOrigin(origins = "*")
    public String resetKeycloakPassword(@PathVariable String username, @PathVariable String newPassword) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.resetPassword(username, newPassword);
    }
}
