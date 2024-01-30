package com.example.demo;

import com.example.demo.core.domain.model.Product;
import com.example.demo.core.domain.service.impl.ProductService;
import com.example.demo.port.user.controller.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitTest_ProductController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

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
}
