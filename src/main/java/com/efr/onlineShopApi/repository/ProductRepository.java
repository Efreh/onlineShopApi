package com.efr.onlineShopApi.repository;

import com.efr.onlineShopApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}