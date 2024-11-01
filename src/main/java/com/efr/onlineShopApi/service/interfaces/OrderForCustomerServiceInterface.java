package com.efr.onlineShopApi.service.interfaces;

import com.efr.onlineShopApi.model.OrderForCustomer;

import java.util.List;
import java.util.Optional;

public interface OrderForCustomerServiceInterface {

    List<OrderForCustomer> getAllOrders();

    Optional<OrderForCustomer> getOrderById(Long id);

    OrderForCustomer createOrder(OrderForCustomer order);

    OrderForCustomer updateOrder(Long id, OrderForCustomer orderDetails);

    void deleteOrder(Long id);
}
