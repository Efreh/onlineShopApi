package com.efr.onlineShopApi.exceptions;

public class OrderForCustomerNotFoundException extends RuntimeException {
    public OrderForCustomerNotFoundException(String message) {
        super(message);
    }
}