package com.efr.onlineShopApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderForCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull(message = "Общая стоимость заказа не должна быть пустой")
    @Min(value = 0, message = "Общая стоимость заказа должна быть положительной")
    private BigDecimal totalPrice;

    @NotNull(message = "Статус заказа не должен быть пустым")
    private OrderStatus status;

    @NotNull(message = "Дата размещения заказа не должна быть пустой")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Дата размещения заказа должна быть сегодня или в прошлом")
    private LocalDate orderDate;

    @NotNull(message = "Адрес доставки не должен быть пустым")
    @Size(min = 10, max = 255, message = "Адрес доставки должен быть от 10 до 255 символов")
    private String shippingAddress;

    @NotNull(message = "Покупатель не должен быть пустым")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "shop_order_products",
            joinColumns = @JoinColumn(name = "shop_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public OrderForCustomer(LocalDate orderDate, String shippingAddress, Customer customer) {
        this.status = OrderStatus.NEW;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.customer = customer;
    }
}