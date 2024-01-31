package com.example.demo;

import com.example.demo.core.domain.model.Product;
import com.example.demo.core.domain.service.impl.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitTest_ProductController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private TestH2Repository h2Repository;

    @AfterEach
    public void tearDown() {
        h2Repository.deleteAll();
    }

    @Test
    public void testAddProduct() throws Exception {

        Product testProduct = new Product("TestName", "TestAuthor", "TestPublisher",
                "TestDescription", "TestImgUrl", 2022, 100, 99.99);

        mockMvc.perform(MockMvcRequestBuilders.post("/add-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(1)).createProduct(Mockito.eq(testProduct));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product("UpdatedName", "UpdatedAuthor", "UpdatedPublisher",
                "UpdatedDescription", "UpdatedImgUrl", 2023, 150, 129.99);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(1)).updateProduct(Mockito.eq(updatedProduct), Mockito.eq(productId));
    }

    @Test
    public void testGetProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        Product mockProduct = new Product("TestName", "TestAuthor", "TestPublisher",
                "TestDescription", "TestImgUrl", 2022, 100, 99.99);

        given(productService.getProduct(productId)).willReturn(mockProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("TestAuthor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value("TestPublisher"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("TestDescription"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl").value("TestImgUrl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishYear").value(2022))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pages").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(99.99));

        Mockito.verify(productService, Mockito.times(1)).getProduct(Mockito.eq(productId));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> mockProducts = Arrays.asList(
                new Product("TestName1", "TestAuthor1", "TestPublisher1",
                        "TestDescription1", "TestImgUrl1", 2022, 100, 99.99),
                new Product("TestName2", "TestAuthor2", "TestPublisher2",
                        "TestDescription2", "TestImgUrl2", 2023, 150, 129.99)
        );

        given(productService.getAllProducts()).willReturn(mockProducts);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("TestName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("TestAuthor1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("TestName2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("TestAuthor2"));

        Mockito.verify(productService, Mockito.times(1)).getAllProducts();
    }

    @Test
    public void testDeleteProduct() throws Exception {

        UUID productIdToDelete = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", productIdToDelete))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(1)).deleteProduct(productIdToDelete);
    }
}
