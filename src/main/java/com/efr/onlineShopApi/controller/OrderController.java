package com.efr.onlineShopApi.controller;

import com.efr.onlineShopApi.exceptions.OrderForCustomerNotFoundException;
import com.efr.onlineShopApi.model.OrderForCustomer;
import com.efr.onlineShopApi.service.interfaces.OrderForCustomerServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderForCustomerServiceInterface orderService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderController(OrderForCustomerServiceInterface orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrderById(@PathVariable Long id) throws JsonProcessingException {
        OrderForCustomer order = orderService.getOrderById(id)
                .orElseThrow(() -> new OrderForCustomerNotFoundException("Заказ с id " + id + " не найден"));
            String orderJson = objectMapper.writeValueAsString(order);
            return ResponseEntity.ok(orderJson);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@Valid @RequestBody String orderJson) throws JsonProcessingException {
            OrderForCustomer order = objectMapper.readValue(orderJson, OrderForCustomer.class);
            orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderJson);
    }
}
