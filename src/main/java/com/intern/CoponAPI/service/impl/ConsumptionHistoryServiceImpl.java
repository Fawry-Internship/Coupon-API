package com.intern.CoponAPI.service.impl;

import com.intern.CoponAPI.exception.RecordNotFoundException;
import com.intern.CoponAPI.exception.ValidationException;
import com.intern.CoponAPI.model.ConsumptionRequest;
import com.intern.CoponAPI.repository.ConsumptionHistoryRepository;
import com.intern.CoponAPI.repository.CouponRepository;
import com.intern.CoponAPI.entity.ConsumptionHistory;
import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.enums.DiscountType;
import com.intern.CoponAPI.service.ConsumptionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConsumptionHistoryServiceImpl implements ConsumptionHistoryService {
    private final CouponRepository couponRepository;
    private final ConsumptionHistoryRepository consumptionHistoryRepository;
    @Override
    public List<ConsumptionHistory> getCouponConsumptionHistories(Long couponId) {
        log.info("Admin want to get Coupon Consumption Histories with id {}", couponId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> {
                    log.error("This coupon with id {}, doesn't Exist!", couponId);
                    return new RecordNotFoundException("This coupon with id "+ couponId + "doesn't exist");
                });
        List<ConsumptionHistory> consumptionHistories = coupon.getConsumptionHistories();
        log.info("Consumption Histories : {}", consumptionHistories);
        return consumptionHistories;
    }

    @Override
    public Double consumeCoupon(ConsumptionRequest consumptionRequest) {
        String couponCode = consumptionRequest.getCouponCode();
        log.info("customer want to consume coupon with code {}", couponCode);
        Coupon coupon = couponRepository.findByCode(consumptionRequest.getCouponCode())
                .orElseThrow(() -> {
                    log.error("coupon with code {}, doesn't exist!", couponCode);
                    return new RecordNotFoundException("coupon with code "  + couponCode + " not found");
                });
        boolean isValidCoupon = isValidCoupon(couponCode);
        if(!isValidCoupon){
            log.error("Coupon with code {}, Not valid any more", couponCode);
            throw new ValidationException("Coupon with code " + couponCode + " Not valid any more");
        }
        double amount = consumptionRequest.getAmount();
        DiscountType discount_type = coupon.getDiscountType();
        Integer discount_value = coupon.getDiscountValue();
        double amountAfterDiscount = calcAmountAfterDiscount(amount, discount_type , discount_value);
        Integer remainingCount = coupon.getRemainingCount();
        coupon.setRemainingCount(remainingCount -1);
        couponRepository.save(coupon);
        addToConsumptionHistory(coupon, consumptionRequest);
        log.info("coupon with code {}, consumed successfully and this amount after discount ${}", couponCode, amountAfterDiscount);
        return amountAfterDiscount;
    }

    private void addToConsumptionHistory(Coupon coupon, ConsumptionRequest consumptionRequest) {
        ConsumptionHistory consumptionHistory = new ConsumptionHistory();
        consumptionHistory.setConsumptionDate(LocalDateTime.now());
        consumptionHistory.setCoupon(coupon);
        consumptionHistory.setCustomerEmail(consumptionRequest.getCustomerEmail());
        consumptionHistory.setOrderId(consumptionRequest.getOrderId());
        consumptionHistory.setDiscountValue(coupon.getDiscountValue());
        consumptionHistoryRepository.save(consumptionHistory);
        log.info("consumption History Added successfully , this Details : {}", consumptionHistory);
    }

    private boolean isValidCoupon(String couponCode) {
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

    private double calcAmountAfterDiscount(double amount, DiscountType type,double discount_value ){
        double amountAfterDiscount = 0;
        if(type == DiscountType.FIXED){
            amountAfterDiscount = amount - discount_value;
        }else if (type == DiscountType.PERCENTAGE){
            double actualDiscountValue = (amount * discount_value)/100;
            amountAfterDiscount = amount - actualDiscountValue;
        }
        return amountAfterDiscount;
    }
}
