package com.efr.onlineShopApi.controller;

import com.efr.onlineShopApi.exceptions.ProductNotFoundException;
import com.efr.onlineShopApi.model.Product;
import com.efr.onlineShopApi.service.ProductService;
import com.efr.onlineShopApi.service.interfaces.ProductServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {
    private final ProductServiceInterface productService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllProducts() throws JsonProcessingException {

        List<Product> products = productService.getAllProducts();
            String productsJson = objectMapper.writeValueAsString(products);
            return ResponseEntity.ok(productsJson);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProductById(@PathVariable Long id) throws JsonProcessingException {
        Product product = productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException("Продукт с id " + id + " не найден"));
            String productJson = objectMapper.writeValueAsString(product);
            return ResponseEntity.ok(productJson);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createProduct(@Valid @RequestBody String productJson) throws JsonProcessingException {
            Product product = objectMapper.readValue(productJson, Product.class);
            productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productJson);
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @Valid @RequestBody String productJson) throws JsonProcessingException {
            Product product = objectMapper.readValue(productJson, Product.class);
            productService.updateProduct(id, product);
            return ResponseEntity.ok(productJson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById (@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
