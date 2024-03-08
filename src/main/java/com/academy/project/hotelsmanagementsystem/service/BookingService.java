package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingService {
    PageDTO<BookingDTO> findAll(Pageable pageable);

    BookingDTO addBooking(@Valid BookingDTO req);

    Optional<BookingDTO> findBookingById(Long id);

    BookingDTO updateBooking(Long id, @Valid BookingDTO req);

    void deleteBooking(Long id);
}
