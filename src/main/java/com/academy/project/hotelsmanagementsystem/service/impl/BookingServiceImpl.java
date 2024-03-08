package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.repository.BookingRepository;
import com.academy.project.hotelsmanagementsystem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.BookingMapper.*;

@Service
@Validated
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public PageDTO<BookingDTO> findAll(Pageable pageable) {
        return toPageImpl(bookingRepository.findAll(pageable),BOOKING_MAPPER);
    }

    @Override
    public BookingDTO addBooking(@Valid BookingDTO req) {
        var entity=bookingRepository.save(BOOKING_MAPPER.toEntity(req));
        return BOOKING_MAPPER.toDto(entity);
    }

    @Override
    public Optional<BookingDTO> findBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(BOOKING_MAPPER::toDto);
    }

    @Override
    public BookingDTO updateBooking(Long id, @Valid BookingDTO req) {
        req.setId(id);
        return bookingRepository.findById(id)
                .map(o->BOOKING_MAPPER.toEntity(req))
                .map(bookingRepository::save)
                .map(BOOKING_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deleteBooking(Long id) {

    }
}
