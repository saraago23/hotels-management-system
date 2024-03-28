package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.CreateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.DisplayBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.UpdateBookingDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BookingMapper extends BaseMapper<BookingEntity, BookingDTO>{
    BookingMapper BOOKING_MAPPER= Mappers.getMapper(BookingMapper.class);


    @Mapping(target="deleted",ignore = true)
    BookingEntity toEntity(UpdateBookingDTO updateBookingDTO);


    @Mapping(target="deleted",ignore = true)
    BookingDTO toDto (BookingEntity bookingEntity);
    BookingEntity toEntity(DisplayBookingDTO displayBookingDTO);
}
