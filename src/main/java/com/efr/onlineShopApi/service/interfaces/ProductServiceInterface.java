package com.efr.onlineShopApi.service.interfaces;

import com.efr.onlineShopApi.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);
}
