package com.efr.onlineShopApi.service;

import com.efr.onlineShopApi.exceptions.ProductNotFoundException;
import com.efr.onlineShopApi.model.Product;
import com.efr.onlineShopApi.repository.ProductRepository;
import com.efr.onlineShopApi.service.interfaces.ProductServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, Product productDetails) {
        return repository.findById(id).map(product -> {
            product.setDescription(productDetails.getDescription());
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setQuantityInStock(productDetails.getQuantityInStock());
            return repository.save(product);
        }).orElseThrow(() -> new ProductNotFoundException("Продукт с id " +id+ " не найден"));
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
