package com.example.demo;


import com.example.demo.core.domain.model.Product;
import com.example.demo.core.domain.service.interfaces.IProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class IntTest_ProductController {

    @LocalServerPort
    private int port;

    private String url = "http://localhost";
    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @BeforeAll
    public static void init(){ restTemplate = new RestTemplate(); }

    @BeforeEach
    public void setUp() {
        url = url.concat(":").concat(port + "").concat("/product");
    }

    @Test
    public void TestAddProduct(){
        Product product = new Product("test", "test", "test", "test", "test", 2000, 2000, 100);

        restTemplate.postForObject(url, product, Product.class);

        assertEquals(1, h2Repository.findAll().size());

        //assertEquals(response., response.getName());
    }














}
