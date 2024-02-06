package com.example.product;

import com.example.product.core.domain.model.Product;
import com.example.product.core.domain.service.impl.ProductService;
import com.example.product.core.domain.service.interfaces.IProductRepository;
import com.example.product.port.user.exception.ProductNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitTest_ProductService {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private IProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldGetProduct() {
        Product productToFind = new Product("ToFindName", "ToFindAuthor", "ToFindPublisher",
                "ToFindDescription", "ToFindImgUrl", 2022, 100, 99.99);
        Product savedProduct = productRepository.save(productToFind);

        Product foundProduct = productService.getProduct(savedProduct.getId());

        assertNotNull(foundProduct);
        assertEquals(savedProduct.getId(), foundProduct.getId());

        Optional<Product> nonExistentProduct = productRepository.findById(UUID.randomUUID());
        assertTrue(nonExistentProduct.isEmpty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(UUID.randomUUID()));
    }

    @Test
    public void shouldGetAllProducts() {
        Product product1 = new Product("Name1", "Author1", "Publisher1",
                "Description1", "ImgUrl1", 2022, 100, 99.99);
        Product product2 = new Product("Name2", "Author2", "Publisher2",
                "Description2", "ImgUrl2", 2022, 150, 129.99);

        productRepository.saveAll(List.of(product1, product2));

        List<Product> allProducts = productService.getAllProducts();

        assertEquals(2, allProducts.size());
        assertEquals(product1.getName(), allProducts.get(0).getName());
        assertEquals(product2.getName(), allProducts.get(1).getName());
        assertEquals(product1.getPages(), allProducts.get(0).getPages());
        assertEquals(product2.getPrice(), allProducts.get(1).getPrice());

        productRepository.deleteAll();
        List<Product> emptyProducts = productService.getAllProducts();
        assertEquals(0, emptyProducts.size());
    }

    @Test
    public void shouldPostProduct() {
        Product product = new Product("TestName", "TestAuthor", "TestPublisher",
                "TestDescription", "TestImgUrl", 2022, 100, 99.99);
        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct.getId());
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getAuthor(), createdProduct.getAuthor());
        assertEquals(product.getPages(), createdProduct.getPages());
        assertEquals(product.getPrice(), createdProduct.getPrice());

        Product retrievedProduct = productRepository.findById(createdProduct.getId()).orElse(null);
        assertNotNull(retrievedProduct);
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getAuthor(), retrievedProduct.getAuthor());
        assertEquals(product.getPages(), retrievedProduct.getPages());
        assertEquals(product.getPrice(), retrievedProduct.getPrice());
    }

    @Test
    public void shouldUpdateProduct() {

        Product originalProduct = new Product("OriginalName", "OriginalAuthor", "OriginalPublisher",
                "OriginalDescription", "OriginalImgUrl", 2022, 100, 99.99);
        Product savedProduct = productRepository.save(originalProduct);
        Product updatedProduct = new Product("UpdatedName", "UpdatedAuthor", "UpdatedPublisher",
                "UpdatedDescription", "UpdatedImgUrl", 2023, 150, 129.99);

        Product result = productService.updateProduct(updatedProduct, savedProduct.getId());

        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getAuthor(), result.getAuthor());
        assertEquals(updatedProduct.getPages(), result.getPages());
        assertEquals(updatedProduct.getPrice(), result.getPrice());

        Product updatedProductInDatabase = productRepository.findById(savedProduct.getId()).orElse(null);
        assertNotNull(updatedProductInDatabase);
        assertEquals(updatedProduct.getName(), updatedProductInDatabase.getName());
        assertEquals(updatedProduct.getAuthor(), updatedProductInDatabase.getAuthor());
        assertEquals(updatedProduct.getPages(), updatedProductInDatabase.getPages());
        assertEquals(updatedProduct.getPrice(), updatedProductInDatabase.getPrice());
    }

    @Test
    public void shouldDeleteProduct() {
        Product productToDelete = new Product("ToDeleteName", "ToDeleteAuthor", "ToDeletePublisher",
                "ToDeleteDescription", "ToDeleteImgUrl", 2022, 100, 99.99);
        Product savedProduct = productRepository.save(productToDelete);

        productService.deleteProduct(savedProduct.getId());

        assertFalse(productRepository.existsById(savedProduct.getId()));

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(UUID.randomUUID()));
    }

    @Test
    public void shouldGetCountOfProducts() {
        // Arrange
        Product product1 = new Product("Name1", "Author1", "Publisher1",
                "Description1", "ImgUrl1", 2022, 100, 99.99);
        Product product2 = new Product("Name2", "Author2", "Publisher2",
                "Description2", "ImgUrl2", 2022, 150, 129.99);

        productRepository.saveAll(List.of(product1, product2));

        long productCount = productService.getCountOfProducts();

        assertEquals(2, productCount);

        productRepository.deleteAll();
        long emptyProductCount = productService.getCountOfProducts();
        assertEquals(0, emptyProductCount);
    }
}
