package com.efr.onlineShopApi.controller;

import com.efr.onlineShopApi.exceptions.OrderForCustomerNotFoundException;
import com.efr.onlineShopApi.model.OrderForCustomer;
import com.efr.onlineShopApi.service.OrderForCustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderForCustomerService orderService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderByIdSuccess() throws Exception {
        Long orderId = 1L;
        OrderForCustomer order = new OrderForCustomer();
        order.setOrderId(orderId);
        order.setTotalPrice(new BigDecimal("100.00"));
        order.setOrderDate(LocalDate.now());

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));
        when(objectMapper.writeValueAsString(order)).thenReturn("{\"orderId\":1,\"totalPrice\":\"100.00\"}");

        ResponseEntity<String> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"orderId\":1,\"totalPrice\":\"100.00\"}", response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    public void testGetOrderByIdNotFound() {
        Long orderId = 2L;

        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        try {
            orderController.getOrderById(orderId);
        } catch (OrderForCustomerNotFoundException | JsonProcessingException e) {
            assertEquals("Заказ с id 2 не найден", e.getMessage());
        }
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    public void testCreateOrderSuccess() throws Exception {
        OrderForCustomer order = new OrderForCustomer();
        order.setTotalPrice(new BigDecimal("150.00"));
        order.setOrderDate(LocalDate.now());

        String orderJson = "{\"totalPrice\":\"150.00\",\"orderDate\":\"2024-10-01\"}";
        when(objectMapper.readValue(orderJson, OrderForCustomer.class)).thenReturn(order);

        ResponseEntity<String> response = orderController.createOrder(orderJson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderJson, response.getBody());
        verify(orderService, times(1)).createOrder(order);
    }
}
