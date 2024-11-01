package com.efr.onlineShopApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotNull(message = "Имя покупателя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя покупателя должно быть от 2 до 50 символов")
    private String firstName;

    @NotNull(message = "Фамилия покупателя не должна быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия покупателя должна быть от 2 до 50 символов")
    private String lastName;

    @NotNull(message = "Email покупателя не должен быть пустым")
    @Email(message = "Некорректный формат электронной почты")
    private String email;

    @NotNull(message = "Контактный номер покупателя не должен быть пустым")
    @Size(min = 10, max = 15, message = "Контактный номер покупателя должен быть от 10 до 15 символов")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Контактный номер должен содержать только цифры и может начинаться с '+'")
    private String contactNumber;

    public Customer(String firstName, String lastName, String email, String contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
    }
}