package com.efr.onlineShopApi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull(message = "Название продукта не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название продукта должно быть от 2 до 100 символов")
    private String name;

    @Size(max = 255, message = "Описание продукта должно быть до 255 символов")
    private String description;

    @NotNull(message = "Цена продукта не должна быть пустой")
    @Min(value = 0, message = "Цена продукта должна быть положительной")
    private BigDecimal price;

    @Min(value = 0, message = "Количество на складе не может быть отрицательным")
    private int quantityInStock;

    public Product(String name, String description, BigDecimal price, int quantityInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
}