package com.intern.CoponAPI.resource;

import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.dto.CouponRequestDTO;
import com.intern.CoponAPI.model.dto.CouponResponseDTO;
import com.intern.CoponAPI.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("coupon")
@RequiredArgsConstructor
public class CouponResource {

    private final CouponService couponService;

    @PostMapping("create")
    public ResponseEntity<Coupon> createdCoupon(@RequestBody CouponRequestDTO couponRequestDTO){
        Coupon createdCoupon = couponService.createCoupon(couponRequestDTO);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @GetMapping("allCoupons")
    public ResponseEntity<List<CouponResponseDTO>> viewAllCoupons(){
        return ResponseEntity.ok(couponService.viewAllCoupons());
    }

    @GetMapping("{couponId}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable Long couponId){
        CouponResponseDTO coupon= couponService.getCouponById(couponId);
        return ResponseEntity.ok(coupon);
    }

    @DeleteMapping("{couponId}")
    public ResponseEntity<String> deleteCouponByID(@PathVariable Long couponId){
        return ResponseEntity.ok(couponService.deleteCouponByID(couponId));
    }

    @PutMapping("{couponId}")
    public ResponseEntity<String> updateCoupon(@PathVariable Long couponId, @RequestBody CouponRequestDTO couponRequestDTO){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(couponService.updateCoupon(couponId, couponRequestDTO));
    }

    @GetMapping("validation")
    public ResponseEntity<Boolean>validateCoupon(@RequestParam String couponCode) {
        return ResponseEntity.ok(couponService.validateCoupon(couponCode));
    }

    @GetMapping("discount")
    public ResponseEntity<Double> calcAmountAfterCouponDiscount(@RequestParam String couponCode, @RequestParam double amount){
        return ResponseEntity.ok(couponService.calcAmountAfterCouponDiscount(couponCode, amount));
    }
}
