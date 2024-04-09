package com.intern.CoponAPI.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionRequest {
    @NotNull(message = "Order ID must not be null")
    private Long orderId;

    @PositiveOrZero(message = "Amount must be a positive number or zero")
    private double amount;

    @NotBlank(message = "Customer email must not be blank")
    @Email(message = "Customer email must be a valid email address")
    private String customerEmail;

    @NotBlank(message = "coupon_code must not be blank")
    private String couponCode;
}
