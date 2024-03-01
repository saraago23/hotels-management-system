package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface HotelMapper extends BaseMapper<HotelEntity, HotelDTO>{
    HotelMapper HOTEL_MAPPER= Mappers.getMapper(HotelMapper.class);
}
