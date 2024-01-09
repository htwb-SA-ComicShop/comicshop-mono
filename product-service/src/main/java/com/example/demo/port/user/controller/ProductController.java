package com.example.demo.port.user.controller;

import com.example.demo.core.domain.model.Product;
import com.example.demo.core.domain.service.interfaces.IProductService;
import com.example.demo.port.user.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping(path = "/product")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable UUID id) {
        Product product = productService.getProduct(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return product;
    }

    @DeleteMapping(path = "/product")
    public @ResponseBody void deleteProduct(@RequestBody UUID productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/products")
    public @ResponseBody List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/seed-database")
    public void seedDataBase() {
        if(productService.getCountOfProducts() == 0){
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
