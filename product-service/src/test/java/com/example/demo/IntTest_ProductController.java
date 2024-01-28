package com.example.demo;

import com.example.demo.core.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntTest_ProductController {

    @LocalServerPort
    private int port;

    private String url = "http://localhost";
    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @AfterEach
    public void tearDown() { h2Repository.deleteAll(); }


   @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void startingDatabaseShouldBeEmpty(){
        assertEquals(0, h2Repository.findAll().size());
    }

    @Test
    public void TestAddingTwoProducts(){
        url = url.concat(":").concat(port + "").concat("/add-product");

        List<Product> productsInH2 = h2Repository.findAll();
        assertEquals(0, productsInH2.size());

        Product product1 = new Product("name1", "author1", "publisher1", "description1", "imageUrl1", 2001, 201, 101);
        Product product2 = new Product("name2", "author2", "publisher2", "description2", "imageUrl2", 2002, 202, 102);

        restTemplate.postForObject(url, product1, Product.class);
        restTemplate.postForObject(url, product2, Product.class);

        productsInH2 = h2Repository.findAll();

        assertEquals(2, productsInH2.size());
        assertEquals("name1", productsInH2.get(0).getName());
        assertEquals("name2", productsInH2.get(1).getName());
        assertEquals(2001, productsInH2.get(0).getPublishYear());
        assertEquals(2002, productsInH2.get(1).getPublishYear());
    }
}
