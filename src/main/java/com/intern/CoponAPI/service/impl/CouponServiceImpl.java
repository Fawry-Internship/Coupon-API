package com.intern.CoponAPI.service.impl;

import com.intern.CoponAPI.exceptions.ValidationException;
import com.intern.CoponAPI.mapper.CouponMapper;
import com.intern.CoponAPI.model.dto.CouponDto;
import com.intern.CoponAPI.repository.CouponRepository;
import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon Already exist");
        }else {
            coupon.setCreated_at(LocalDateTime.now());
            coupon.setRemaining_count(coupon.getUsage_limit());
            return couponRepository.save(coupon);
        }
    }
    public List<Coupon> viewAllCoupons(){
        return couponRepository.findAll();
    }

    public CouponDto getCouponById(Long couponId){
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        return couponMapper.toDTO(coupon.get());
    }
}
