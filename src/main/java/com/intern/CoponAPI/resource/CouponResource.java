package com.intern.CoponAPI.resource;

import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.dto.CouponDto;
import com.intern.CoponAPI.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("coupon")
@RequiredArgsConstructor
public class CouponResource {

    private final CouponService couponService;

    @PostMapping("create")
    public ResponseEntity<?> createdCoupon(@RequestBody CouponDto couponDto){
        Coupon createdCoupon = couponService.createCoupon(couponDto);
        return ResponseEntity.ok(createdCoupon);
    }

    @GetMapping("allCoupons")
    public ResponseEntity<List<Coupon>> viewAllCoupons(){
        return ResponseEntity.ok(couponService.viewAllCoupons());
    }

    @GetMapping("{couponId}")
    public ResponseEntity<Optional<Coupon>> getCouponById(@PathVariable Long couponId){
        Optional<Coupon> coupon= couponService.getCouponById(couponId);
        return ResponseEntity.ok(coupon);
    }
}
