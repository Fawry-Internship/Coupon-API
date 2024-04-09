package com.intern.CoponAPI.model.dto;

import com.intern.CoponAPI.model.enums.DiscountType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CouponResponseDTO {
    private Integer id;
    private String code;
    private DiscountType discountType;
    private String discountValue;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String usageLimit;
    private String remainingCount;
    private LocalDateTime createdAt;
}
