package com.example.demo;

import com.example.demo.TestH2Repository;
import com.example.demo.core.domain.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IntTest_ProductController {

    @LocalServerPort
    private int port;

    private String url = "http://localhost:";
    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @AfterEach
    public void tearDown() {
        h2Repository.deleteAll();
    }

   @Test
    public void addProductTest() throws Exception {

        url = url + port + "/add-product";

        Product product1 = new Product("name1", "author1", "publisher1", "description1", "imageUrl1", 2001, 201, 101.00);
        Product product2 = new Product("name2", "author2", "publisher2", "description2", "imageUrl2", 2002, 202, 102.00);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString((product1))))
                .andExpect(status().isOk());

       mockMvc.perform(MockMvcRequestBuilders.post(url)
               .contentType(MediaType.APPLICATION_JSON)
               .content(new ObjectMapper().writeValueAsString((product2))))
               .andExpect(status().isOk());

       List<Product> productsInH2 = h2Repository.findAll();

       assertEquals(2, productsInH2.size());
       assertEquals(productsInH2.get(0).getName(), product1.getName());
       assertEquals(productsInH2.get(0).getAuthor(), product1.getAuthor());
       assertEquals(productsInH2.get(0).getPublisher(), product1.getPublisher());
       assertEquals(productsInH2.get(0).getDescription(), product1.getDescription());
       assertEquals(productsInH2.get(0).getImgUrl(), product1.getImgUrl());
       assertEquals(productsInH2.get(0).getPublishYear(), product1.getPublishYear());
       assertEquals(productsInH2.get(0).getPages(), product1.getPages());
       assertEquals(productsInH2.get(0).getPrice(), product1.getPrice());
    }

    @Test
    public void getProductTest() throws Exception{
        Product testProduct = new Product("testName", "testAuthor", "testPublisher", "testDescription", "testImageUrl", 200, 100, 10.00);
        String apiUUID = UUID.randomUUID().toString();
        String insertUUID = apiUUID.replace("-", "");
        String insertSQL = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID + "', '" + testProduct.getName() + "', '" + testProduct.getAuthor() + "', '" + testProduct.getPublisher() + "', '" + testProduct.getDescription() + "', '" + testProduct.getImgUrl() + "', " + testProduct.getPublishYear() + ", " + testProduct.getPages() + ", " + testProduct.getPrice() + ")";
        url = url + port + "/product/" + apiUUID;

        jdbcTemplate.execute(insertSQL);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", apiUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(apiUUID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testProduct.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(testProduct.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(testProduct.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testProduct.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl").value(testProduct.getImgUrl()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishYear").value(testProduct.getPublishYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pages").value(testProduct.getPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testProduct.getPrice()));
    }
}
