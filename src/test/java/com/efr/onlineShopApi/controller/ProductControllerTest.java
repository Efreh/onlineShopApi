package com.efr.onlineShopApi.controller;

import com.efr.onlineShopApi.exceptions.ProductNotFoundException;
import com.efr.onlineShopApi.model.Product;
import com.efr.onlineShopApi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProductsSuccess() throws Exception {
        Product product = new Product("Product1", "Description1", new BigDecimal("50.00"), 10);
        List<Product> products = Arrays.asList(product);

        when(productService.getAllProducts()).thenReturn(products);
        when(objectMapper.writeValueAsString(products)).thenReturn("[{\"name\":\"Product1\",\"price\":\"50.00\"}]");

        ResponseEntity<String> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"name\":\"Product1\",\"price\":\"50.00\"}]", response.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductByIdSuccess() throws Exception {
        Long productId = 1L;
        Product product = new Product("Product1", "Description1", new BigDecimal("50.00"), 10);
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(objectMapper.writeValueAsString(product)).thenReturn("{\"name\":\"Product1\",\"price\":\"50.00\"}");

        ResponseEntity<String> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"name\":\"Product1\",\"price\":\"50.00\"}", response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testGetProductByIdNotFound() {
        Long productId = 2L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        try {
            productController.getProductById(productId);
        } catch (ProductNotFoundException e) {
            assertEquals("Продукт с id 2 не найден", e.getMessage());
        }
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testCreateProductSuccess() throws Exception {
        Product product = new Product("Product1", "Description1", new BigDecimal("50.00"), 10);
        String productJson = "{\"name\":\"Product1\",\"price\":\"50.00\"}";

        when(objectMapper.readValue(productJson, Product.class)).thenReturn(product);

        ResponseEntity<String> response = productController.createProduct(productJson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productJson, response.getBody());
        verify(productService, times(1)).createProduct(product);
    }
}
