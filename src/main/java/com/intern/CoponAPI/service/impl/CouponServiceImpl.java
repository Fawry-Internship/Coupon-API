package com.intern.CoponAPI.service.impl;

import com.intern.CoponAPI.exception.ConflictException;
import com.intern.CoponAPI.exception.RecordNotFoundException;
import com.intern.CoponAPI.mapper.CouponMapper;
import com.intern.CoponAPI.model.dto.CouponRequestDTO;
import com.intern.CoponAPI.model.dto.CouponResponseDTO;
import com.intern.CoponAPI.model.enums.DiscountType;
import com.intern.CoponAPI.repository.CouponRepository;
import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public Coupon createCoupon(CouponRequestDTO couponRequestDTO){
        log.info("Admin want to create coupon with this details {}", couponRequestDTO);

        String couponCode = couponRequestDTO.getCode();
        if(couponRepository.existsByCode(couponCode)){
            log.error("This coupon with code {} Already Exist!", couponCode);
            throw new ConflictException("Coupon Already exist");
        }else {
            Coupon coupon = couponMapper.toEntity(couponRequestDTO);
            coupon.setCreatedAt(LocalDateTime.now());
            coupon.setRemainingCount(coupon.getUsageLimit());
            couponRepository.save(coupon);
            log.info("coupon saved successfully");
            return coupon;
        }
    }
    public List<CouponResponseDTO> viewAllCoupons(){
        log.info("Admin want to get All coupons Details");
        List<CouponResponseDTO> allCouponsDTOs = couponRepository.findAll()
                .stream()
                .map(couponMapper::toDTO)
                .collect(Collectors.toList());
        log.info("All coupons {}", allCouponsDTOs);
        return allCouponsDTOs;
    }

    public CouponResponseDTO getCouponById(Long couponId){
        log.info("Admin want to get coupon details with id {}", couponId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> {
                    log.error("This Coupon with id {}, doesn't Exist!", couponId);
                    return new RecordNotFoundException("This coupon with id " + couponId + ", doesn't Exist!");
                });
        log.info("coupon Details : {}", coupon);
        return couponMapper.toDTO(coupon);
    }

    @Override
    public Boolean validateCoupon(String couponCode) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() ->{
                    log.error("This coupon with code {}, doesn't Exist!", couponCode);
                    return new RecordNotFoundException("This coupon with code "+ couponCode + "doesn't exist");
                });
        if (coupon.getRemainingCount() < 1 || coupon.getValidTo().isBefore(LocalDate.now())){
            return false;
        }
        return true;
    }

    @Override
    public Double calcAmountAfterCouponDiscount(String couponCode, double amount) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() ->{
                    log.error("This coupon with code {}, doesn't Exist!", couponCode);
                    return new RecordNotFoundException("This coupon with code "+ couponCode + "doesn't exist");
                });

        DiscountType discountType = coupon.getDiscountType();
        int discountValue = coupon.getDiscountValue();
        double amountAfterDiscount = 0;
        if(discountType == DiscountType.FIXED){
            amountAfterDiscount = amount - discountValue;
        }else if (discountType == DiscountType.PERCENTAGE){
            double actualDiscountValue = (amount * discountValue)/100;
            amountAfterDiscount = amount - actualDiscountValue;
        }
        return amountAfterDiscount;
    }

    @Override
    public String updateCoupon(Long couponId, CouponRequestDTO couponRequestDTO) {
        Coupon existCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> {
                    log.error("This coupon with id {} doesn't exist", couponId);
                   return new RecordNotFoundException("This coupon with id " + couponId + " doesn't exist");
                });
        log.info("the coupon before update {}", existCoupon);
        Coupon updatedCoupon = couponMapper.toEntity(couponRequestDTO);
        updatedCoupon.setId(couponId);
        updatedCoupon.setRemainingCount(updatedCoupon.getUsageLimit() - (existCoupon.getUsageLimit() - existCoupon.getRemainingCount()));
        updatedCoupon.setCreatedAt(existCoupon.getCreatedAt());
        couponRepository.save(updatedCoupon);
        log.info("coupon after update {}", updatedCoupon);

        return "success";
    }

    @Override
    public String deleteCouponByID(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> {
                    log.error("This coupon with id {} Already doesn't exist ", couponId);
                   return new RecordNotFoundException("this coupon with id " + couponId + " already doesn't exist!");
                });
        couponRepository.delete(coupon);
        log.info("deleted successfully");
        return "success";
    }
}
