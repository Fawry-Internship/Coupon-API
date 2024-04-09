package com.intern.CoponAPI.model.dto;

import com.intern.CoponAPI.model.enums.DiscountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequestDTO {
    @NotBlank(message = "Coupon code must not be blank")
    private String code;

    @NotNull(message = "Discount type must not be null")
    private DiscountType discountType;

    @Min(value = 0, message = "Discount value must be a positive integer")
    private int discountValue;

    @NotNull(message = "Valid from date must not be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Valid from date must be in the format YYYY-MM-DD")
    private LocalDate validFrom;

    @NotNull(message = "Valid to date must not be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Valid to date must be in the format YYYY-MM-DD")
    private LocalDate validTo;

    @Min(value = 0, message = "Usage limit must be a positive integer")
    private int usageLimit;
}
