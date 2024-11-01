package com.efr.onlineShopApi.repository;

import com.efr.onlineShopApi.model.OrderForCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderForCustomerRepository extends JpaRepository<OrderForCustomer,Long> {
}
