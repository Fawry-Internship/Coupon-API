package com.intern.CoponAPI.mapper;

import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.dto.CouponRequestDTO;
import com.intern.CoponAPI.model.dto.CouponResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    CouponResponseDTO toDTO(Coupon coupon);

    Coupon toEntity(CouponRequestDTO couponRequestDTO);
}
