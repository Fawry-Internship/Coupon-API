package com.intern.CoponAPI.resource;

import com.intern.CoponAPI.model.ConsumptionRequest;
import com.intern.CoponAPI.entity.ConsumptionHistory;
import com.intern.CoponAPI.service.ConsumptionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("coupon")
public class ConsumptionResource {
    private final ConsumptionHistoryService consumptionHistoryService;
    @GetMapping("consumption-history/{couponId}")
    public ResponseEntity<List<ConsumptionHistory>> getCouponConsumptionHistories(@PathVariable Long couponId){
        return ResponseEntity.ok(consumptionHistoryService.getCouponConsumptionHistories(couponId));
    }

    @PostMapping("consume")
    public ResponseEntity<Double> consumeCoupon (@RequestBody ConsumptionRequest consumptionRequest){
        return new ResponseEntity<>(consumptionHistoryService.consumeCoupon(consumptionRequest), HttpStatus.ACCEPTED);
    }
}
