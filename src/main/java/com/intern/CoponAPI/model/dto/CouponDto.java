package com.intern.CoponAPI.model.dto;

import com.intern.CoponAPI.model.enums.DiscountType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponDto {
  //  private Integer id;
    private String code;
    private DiscountType discount_type;
    private String discount_value;
    private LocalDate valid_from;
    private LocalDate valid_to;
    private String usage_limit;
    private String remaining_count;
}
