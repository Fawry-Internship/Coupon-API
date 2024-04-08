package com.intern.CoponAPI.repository;

import com.intern.CoponAPI.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByCode(String code);

    Optional<Coupon> findByCode(String couponCode);

    Optional<Coupon> findById(Long id);
}
