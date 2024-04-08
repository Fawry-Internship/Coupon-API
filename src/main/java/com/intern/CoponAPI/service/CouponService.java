package com.intern.CoponAPI.service;

import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.dto.CouponDto;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Coupon createCoupon(CouponDto couponDto);
    List<Coupon> viewAllCoupons();
    Optional<Coupon> getCouponById(Long couponId);
}
