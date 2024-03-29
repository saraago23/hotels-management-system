package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.CreateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.UpdateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    PageDTO<BookingDTO> findAll(Pageable pageable);
    PageDTO<BookingDTO> findAllDeleted(Pageable pageable);

    BookingDTO addBooking(@Valid CreateBookingDTO bookingDTO);

    BookingDTO findBookingById(Long id);
    List<BookingDTO> findBookingsByUserId(Long userId);
    List<BookingDTO> findMyBookings();

    BookingDTO updateBooking(Long id, @Valid UpdateBookingDTO createUpdateBookingDTO);

    void deleteBooking(Long id);
}
