package com.intern.CoponAPI.service;

import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.dto.CouponRequestDTO;
import com.intern.CoponAPI.model.dto.CouponResponseDTO;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(CouponRequestDTO couponRequestDTO);
    List<CouponResponseDTO> viewAllCoupons();
    CouponResponseDTO getCouponById(Long couponId);

    Boolean validateCoupon(String couponCode);

    Double calcAmountAfterCouponDiscount(String couponCode, double amount);
}
