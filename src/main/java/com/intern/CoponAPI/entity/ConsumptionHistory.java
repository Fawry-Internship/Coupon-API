package com.intern.CoponAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "consumption-history")
public class ConsumptionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime consumptionDate;
    private Integer discountValue;
    private Long orderId;
    private String customerEmail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    @JsonIgnore
    private Coupon coupon;

    @Override
    public String toString() {
        return "ConsumptionHistory{" +
                "id=" + id +
                ", consumption_date=" + consumptionDate +
                ", discount_value=" + discountValue +
                ", order_id=" + orderId +
                ", customer_email='" + customerEmail + '\'' +
                '}';
    }
}
