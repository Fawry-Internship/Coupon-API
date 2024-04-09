package com.intern.CoponAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.CoponAPI.model.enums.DiscountType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer discountValue;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer usageLimit;
    private Integer remainingCount;
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coupon")
    @JsonIgnore
    private List<ConsumptionHistory> consumptionHistories;
}
