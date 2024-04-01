package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.CreateUpdateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.DisplayBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    PageDTO<BookingDTO> findAll(Pageable pageable);
    PageDTO<BookingDTO> findAllDeleted(Pageable pageable);

    BookingDTO addBooking(@Valid CreateUpdateBookingDTO bookingDTO);

    BookingDTO findBookingById(Long id);
    List<BookingDTO> findBookingsByUserId(Long userId);
    List<BookingDTO> findMyBookings();

    DisplayBookingDTO updateBooking(Long id, @Valid CreateUpdateBookingDTO updateBookingDTO);

    void deleteBooking(Long id);
}
