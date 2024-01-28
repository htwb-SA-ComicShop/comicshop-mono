package com.example.demo.port.user.controller;

import com.example.demo.core.domain.model.AddToCartDTO;
import com.example.demo.core.domain.model.Product;
import com.example.demo.core.domain.service.interfaces.IProductService;
import com.example.demo.port.shoppingcart.producer.AddProductProducer;
import com.example.demo.port.user.exception.ProductNotFoundException;

import com.example.demo.port.user.producer.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    AddProductProducer addProductProducer;

    @PostMapping(path = "/add-product")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Product product) {
        System.out.println("HEREEEEEEEEEEEEEE*********" + product.getName());
        Product newProduct = new Product(
                product.getName(),
                product.getAuthor(),
                product.getPublisher(),
                product.getDescription(),
                product.getImgUrl(),
                product.getPublishYear(),
                product.getPages(),
                product.getPrice()
        );
        productService.createProduct(newProduct);
    }

    @PutMapping(path = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public @ResponseBody void update(@RequestBody Product product, @PathVariable UUID id) {
        System.out.println("Updating product: " + product);
        productService.updateProduct(product, id);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable UUID id) {
        Product product = productService.getProduct(id);
        if (product == null) throw new ProductNotFoundException(id);
        return product;
    }

    @DeleteMapping(path = "/product/{id}")
    @CrossOrigin(origins = "*")
    public @ResponseBody void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/products")
    public @ResponseBody List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping(path = "/add-to-cart")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void addToCart(@RequestBody Product product) {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> tokenAttributes = authToken.getTokenAttributes();
        String userName = (String) tokenAttributes.get("preferred_username");
        String email = (String) tokenAttributes.get("email");
        System.out.println("Adding to cart: " + product.getName());
        System.out.println("userName: " + userName);
        System.out.println("email: " + email);
        AddToCartDTO cartItem = new AddToCartDTO(userName, email, product);
        addProductProducer.sendToCart(cartItem);
    }

    @GetMapping("/seed-database")
    public void seedDataBase() {
        if (productService.getCountOfProducts() == 0) {
            String dummyDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed suscipit justo leo, vitae tincidunt arcu tristique in. Donec consequat gravida imperdiet.";
            Product a = new Product(
                    "Asterix in Britain",
                    "René Goscinny & Albert Uderzo",
                    "Dargaud",
                    "Asterix, his first-cousin-once-removed Anticlimax and Obelix travel to Britain with a barrel of magic potion that will allow Anticlimax's village to repel the invading Romans.",
                    "https://upload.wikimedia.org/wikipedia/en/4/48/Asterix_Britain.png",
                    1966,
                    99,
                    5.99
            );
            Product b = new Product(
                    "Asterix in Switzerland",
                    "René Goscinny & Albert Uderzo",
                    "Dargaud",
                    dummyDescription,
                    "https://upload.wikimedia.org/wikipedia/en/0/06/Asterix_Switzerland.png",
                    1970,
                    77,
                    5.99
            );
            Product c = new Product(
                    "Asterix in Corsica",
                    "René Goscinny & Albert Uderzo",
                    "Dargaud",
                    dummyDescription,
                    "https://upload.wikimedia.org/wikipedia/en/7/7c/Asterixcover-20.jpg",
                    1973,
                    77,
                    5.99
            );
            Product d = new Product(
                    "Asterix in Spain",
                    "René Goscinny & Albert Uderzo",
                    "Dargaud",
                    dummyDescription,
                    "https://upload.wikimedia.org/wikipedia/en/3/30/Asterixcover-14.jpg",
                    1971,
                    77,
                    5.99
            );
            Product e = new Product(
                    "Asterix in Belgium",
                    "René Goscinny & Albert Uderzo",
                    "Dargaud",
                    dummyDescription,
                    "https://upload.wikimedia.org/wikipedia/en/2/29/Asterix_Belgium.png",
                    1980,
                    77,
                    5.99
            );
            Product f = new Product(
                    "Asterix the Legionary",
                    "René Goscinny & Albert Uderzo",
                    "Dargaud",
                    dummyDescription,
                    "https://upload.wikimedia.org/wikipedia/en/e/ee/Asterixcover-10.jpg",
                    1967,
                    77,
                    5.99
            );
            productService.createProduct(a);
            productService.createProduct(b);
            productService.createProduct(c);
            productService.createProduct(d);
            productService.createProduct(e);
            productService.createProduct(f);
        }
    }

}
