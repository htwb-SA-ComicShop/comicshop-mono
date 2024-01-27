package com.example.demo;


import com.example.demo.core.domain.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntTest_ProductController {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:8080";
    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository test_h2Repository;

    @BeforeAll
    public static void init(){ restTemplate = new RestTemplate(); }

    /*@BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/products");
    }*/

    @Test
    public void TestAddProduct(){
        String url  = baseUrl + "/product";
        Product product = new Product("test", "test", "test", "test", "test", 2000, 2000, 100);

        Product response = restTemplate.postForObject(url, product, Product.class);

        //assertEquals(1, test_h2Repository.findAll().size());
        //assertEquals("test", response.getName());

        System.out.println("*******************" + product.getId());
    }













}
