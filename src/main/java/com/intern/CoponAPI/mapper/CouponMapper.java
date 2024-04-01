package com.intern.CoponAPI.mapper;

import com.intern.CoponAPI.entity.Coupon;
import com.intern.CoponAPI.model.dto.CouponDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    CouponDto toDTO(Coupon coupon);

    Coupon toEntity(CouponDto couponDto);
}
