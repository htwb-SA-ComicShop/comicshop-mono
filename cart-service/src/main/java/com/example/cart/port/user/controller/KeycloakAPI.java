package com.example.cart.port.user.controller;


import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KeycloakAPI {

    public void KeycloakRequest() {}

    public String getAdminToken() throws IOException, InterruptedException {
        String url = "http://localhost:8090/auth/realms/master/protocol/openid-connect/token/";
        String formData = "username=admin&password=admin&client_id=admin-cli&grant_type=password";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String token = new JSONObject(response.body()).getString("access_token");

        return token;
    }

    public String getUserCartId(String userName) throws IOException, InterruptedException {
        String cartId;
        int cartIdStartIndex;
        String authorizationHeaderValue = "Bearer " + getAdminToken();
        String url = "http://localhost:8090/auth/admin/realms/profile-service/users/?username=" + userName + "&exact=true";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeaderValue)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String searchForString = "\"attributes\":{\"cart_id\":[\"";

        if(response.body().indexOf(searchForString) < 0)
            return "NO CART ID";

        cartIdStartIndex = response.body().indexOf(searchForString) + searchForString.length();
        String fromCartIdToEnd = response.body().substring(cartIdStartIndex);
        int cartIdEndingIndex = fromCartIdToEnd.indexOf("\"");
        cartId = fromCartIdToEnd.substring(0, cartIdEndingIndex);

        return cartId;
    }

    public String getUserId(String userName) throws IOException, InterruptedException {
        String authorizationHeaderValue = "Bearer " + getAdminToken();
        String url = "http://localhost:8090/auth/admin/realms/profile-service/users/?username=" + userName + "&exact=true";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeaderValue)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String searchForString = "\"id\":\"";

        int userIdStartIndex = response.body().indexOf(searchForString) + searchForString.length();
        String fromUserIdToEnd = response.body().substring(userIdStartIndex);
        int cartIdEndingIndex = fromUserIdToEnd.indexOf("\"");
        String userId = fromUserIdToEnd.substring(0, cartIdEndingIndex);

        return userId;
    }

    public String resetPassword(String userName, String newPassword) throws IOException, InterruptedException {
        String authorizationHeaderValue = "Bearer " + getAdminToken();
        String thisUserId = getUserId(userName);
        String url = "http://localhost:8090/auth/admin/realms/profile-service/users/" + thisUserId + "/reset-password";

        String jsonPayload = "{\"type\": \"password\", \"value\": \"" + newPassword + "\"}";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeaderValue)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String result;
        if(response.statusCode() == 204)
            result = "PASSWORD CHANGED!";
        else
            result =  "ERROR!";

        return result;
    }

    public String updateCartId(String userName, String newCartId) throws IOException, InterruptedException {

        String authorizationHeaderValue = "Bearer " + getAdminToken();
        String url = "http://localhost:8090/auth/admin/realms/profile-service/users/" + getUserId(userName);

        String jsonPayload = "{\"attributes\": {\"cart_id\": \"" +  newCartId + "\"}}";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authorizationHeaderValue)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String result;
        if(response.statusCode() == 204)
            result = "new cart_id: " + newCartId;
        else
            result =  "ERROR!";

        return result;
    }
}

