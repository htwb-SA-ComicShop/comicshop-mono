package com.example.product;

import java.util.List;
import java.util.UUID;
import com.example.product.core.domain.model.Product;
import com.example.product.port.user.exception.ProductNotFoundException;
import com.example.product.testUtil.ProductRowMapper;
import com.example.product.testUtil.TestH2Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IntTest_ProductController {

    @LocalServerPort
    private int port;

    private String url = "http://localhost:";

    @Autowired
    private TestH2Repository h2Repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        h2Repository.deleteAll();
    }

    @Test
    public void shouldAddProduct() throws Exception {
        url = url + port + "/add-product";
        Product product1 = new Product("name1", "author1", "publisher1", "description1", "imageUrl1", 2001, 201, 101.00);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString((product1))))
                .andExpect(status().isOk());

       String sqlStatement = "SELECT * FROM product";
       List<Product> allProductsInH2 = jdbcTemplate.query(sqlStatement, new ProductRowMapper());

       assertEquals(1, allProductsInH2.size());
       assertEquals(allProductsInH2.get(0).getName(), product1.getName());
       assertEquals(allProductsInH2.get(0).getAuthor(), product1.getAuthor());
       assertEquals(allProductsInH2.get(0).getPublisher(), product1.getPublisher());
       assertEquals(allProductsInH2.get(0).getDescription(), product1.getDescription());
       assertEquals(allProductsInH2.get(0).getImgUrl(), product1.getImgUrl());
       assertEquals(allProductsInH2.get(0).getPublishYear(), product1.getPublishYear());
       assertEquals(allProductsInH2.get(0).getPages(), product1.getPages());
       assertEquals(allProductsInH2.get(0).getPrice(), product1.getPrice());
    }

    @Test
    public void shouldGetProduct() throws Exception{
        Product testProduct = new Product("testName", "testAuthor", "testPublisher", "testDescription", "testImageUrl", 200, 100, 10.00);
        String apiUUID = UUID.randomUUID().toString();
        String insertUUID = apiUUID.replace("-", "");
        String sqlStatement = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID + "', '" + testProduct.getName() + "', '" + testProduct.getAuthor() + "', '" + testProduct.getPublisher() + "', '" + testProduct.getDescription() + "', '" + testProduct.getImgUrl() + "', " + testProduct.getPublishYear() + ", " + testProduct.getPages() + ", " + testProduct.getPrice() + ")";
        url = url + port + "/product/" + apiUUID;
        jdbcTemplate.execute(sqlStatement);

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

    @Test
    public void shouldGetAllProducts() throws Exception {
        Product product1 = new Product("name1", "author1", "publisher1", "description1", "imageUrl1", 1, 1, 1.00);
        Product product2 = new Product("name2", "author2", "publisher2", "description2", "imageUrl2", 2, 2, 3.00);
        Product product3 = new Product("name3", "author3", "publisher3", "description3", "imageUrl3", 3, 3, 3.00);

        String apiUUID1 = UUID.randomUUID().toString();
        String apiUUID2 = UUID.randomUUID().toString();
        String apiUUID3 = UUID.randomUUID().toString();

        String insertUUID1 = apiUUID1.replace("-", "");
        String insertUUID2 = apiUUID2.replace("-", "");
        String insertUUID3 = apiUUID3.replace("-", "");

        String sqlStatement1 = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID1 + "', '" + product1.getName() + "', '" + product1.getAuthor() + "', '" + product1.getPublisher() + "', '" + product1.getDescription() + "', '" + product1.getImgUrl() + "', " + product1.getPublishYear() + ", " + product1.getPages() + ", " + product1.getPrice() + ")";
        String sqlStatement2 = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID2 + "', '" + product2.getName() + "', '" + product2.getAuthor() + "', '" + product2.getPublisher() + "', '" + product2.getDescription() + "', '" + product2.getImgUrl() + "', " + product2.getPublishYear() + ", " + product2.getPages() + ", " + product2.getPrice() + ")";
        String sqlStatement3 = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID3 + "', '" + product3.getName() + "', '" + product3.getAuthor() + "', '" + product3.getPublisher() + "', '" + product3.getDescription() + "', '" + product3.getImgUrl() + "', " + product3.getPublishYear() + ", " + product3.getPages() + ", " + product3.getPrice() + ")";

        String url1 = url + port + "/product/" + apiUUID1;
        String url2 = url + port + "/product/" + apiUUID2;
        String url3 = url + port + "/product/" + apiUUID3;

        jdbcTemplate.execute(sqlStatement1);
        jdbcTemplate.execute(sqlStatement2);
        jdbcTemplate.execute(sqlStatement3);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].author").value(product2.getAuthor()))
                .andExpect(jsonPath("$[2].publisher").value(product3.getPublisher()));
    }

    @Test
    public void getProduct_ifNotExists_shouldReturn404() throws Exception {
        UUID uuid = UUID.randomUUID();
        String apiUUID = uuid.toString();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", apiUUID));

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", apiUUID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        Product testProduct = new Product("Initial Name", "Initial Author", "Initial Publisher", "Initial Description", "Initial ImageUrl", 1, 1, 1.00);
        String apiUUID = UUID.randomUUID().toString();
        String insertUUID = apiUUID.replace("-", "");
        String sqlStatement = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID + "', '" + testProduct.getName() + "', '" + testProduct.getAuthor() + "', '" + testProduct.getPublisher() + "', '" + testProduct.getDescription() + "', '" + testProduct.getImgUrl() + "', " + testProduct.getPublishYear() + ", " + testProduct.getPages() + ", " + testProduct.getPrice() + ")";
        url = url + port + "/product/" + apiUUID;

        jdbcTemplate.execute(sqlStatement);

        testProduct.setName("Updated Name");
        testProduct.setAuthor("Updated Author");
        testProduct.setPublishYear(2);
        testProduct.setPrice(2.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}", apiUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testProduct)))
                .andExpect(status().isOk());

        sqlStatement = "SELECT * FROM product";
        List<Product> allProductsInH2 = jdbcTemplate.query(sqlStatement, new ProductRowMapper());

        assertEquals(1, allProductsInH2.size());
        assertEquals("Updated Name", allProductsInH2.get(0).getName());
        assertEquals("Updated Author", testProduct.getAuthor());
        assertEquals(2, testProduct.getPublishYear());
        assertEquals(2.0, testProduct.getPrice());
    }

    @Test
    public void UpdateProduct_ifNotExists_shouldReturn400() throws Exception {
        String apiUUID = UUID.randomUUID().toString();
        url = url + port + "/product/" + apiUUID;

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}", apiUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {

        Product product1 = new Product("name1", "author1", "publisher1", "description1", "imageUrl1", 1, 1, 1.00);
        Product product2 = new Product("name2", "author2", "publisher2", "description2", "imageUrl2", 2, 2, 3.00);

        String apiUUID1 = UUID.randomUUID().toString();
        String apiUUID2 = UUID.randomUUID().toString();

        String insertUUID1 = apiUUID1.replace("-", "");
        String insertUUID2 = apiUUID2.replace("-", "");

        String sqlStatement1 = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID1 + "', '" + product1.getName() + "', '" + product1.getAuthor() + "', '" + product1.getPublisher() + "', '" + product1.getDescription() + "', '" + product1.getImgUrl() + "', " + product1.getPublishYear() + ", " + product1.getPages() + ", " + product1.getPrice() + ")";
        String sqlStatement2 = "insert into product(id, name, author, publisher, description, imgUrl, publishYear, pages, price) values ('"+ insertUUID2 + "', '" + product2.getName() + "', '" + product2.getAuthor() + "', '" + product2.getPublisher() + "', '" + product2.getDescription() + "', '" + product2.getImgUrl() + "', " + product2.getPublishYear() + ", " + product2.getPages() + ", " + product2.getPrice() + ")";

        jdbcTemplate.execute(sqlStatement1);
        jdbcTemplate.execute(sqlStatement2);

        String sqlStatement = "SELECT * FROM product";
        List<Product> allProductsInH2 = jdbcTemplate.query(sqlStatement, new ProductRowMapper());

        assertEquals(2, allProductsInH2.size());
        boolean productExists = allProductsInH2.stream().anyMatch(product -> product.getId().toString().equals(apiUUID2));
        assertTrue(productExists);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", apiUUID2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        productExists = allProductsInH2.stream().anyMatch(product -> product.getId().equals(apiUUID2));
        assertFalse(productExists);
    }

    @Test
    public void deleteProduct_ifNotExists_shouldReturn404() throws Exception {
        String apiUUID = UUID.randomUUID().toString();
        url = url + port + "/product/" + apiUUID;

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", apiUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
