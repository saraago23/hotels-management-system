package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BookingMapper extends BaseMapper<BookingEntity, BookingDTO>{
    BookingMapper BOOKING_MAPPER= Mappers.getMapper(BookingMapper.class);
}
