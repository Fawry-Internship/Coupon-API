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

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(nullable = false)
    private Integer discountValue;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false)
    private LocalDate validTo;

    @Column(nullable = false)
    private Integer usageLimit;

    private Integer remainingCount;
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coupon")
    @JsonIgnore
    private List<ConsumptionHistory> consumptionHistories;
}
