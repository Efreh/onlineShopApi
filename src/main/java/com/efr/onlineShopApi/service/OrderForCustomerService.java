package com.efr.onlineShopApi.service;

import com.efr.onlineShopApi.exceptions.OrderForCustomerNotFoundException;
import com.efr.onlineShopApi.model.OrderForCustomer;
import com.efr.onlineShopApi.repository.OrderForCustomerRepository;
import com.efr.onlineShopApi.service.interfaces.OrderForCustomerServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderForCustomerService implements OrderForCustomerServiceInterface {

    private final OrderForCustomerRepository repository;

    @Autowired
    public OrderForCustomerService(OrderForCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderForCustomer> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public Optional<OrderForCustomer> getOrderById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public OrderForCustomer createOrder(OrderForCustomer order) {
        return repository.save(order);
    }

    @Transactional
    @Override
    public OrderForCustomer updateOrder(Long id, OrderForCustomer orderDetails) {
        return repository.findById(id).map(order -> {
            order.setCustomer(orderDetails.getCustomer());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setStatus(orderDetails.getStatus());
            order.setProducts(orderDetails.getProducts());
            order.setTotalPrice(orderDetails.getTotalPrice());
            order.setShippingAddress(orderDetails.getShippingAddress());
            return repository.save(order);
        }).orElseThrow(() -> new OrderForCustomerNotFoundException("Заказ с id " + id + " не найден"));
    }

    @Transactional
    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
