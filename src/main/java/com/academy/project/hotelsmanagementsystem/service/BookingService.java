package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.CreateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.UpdateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    PageDTO<BookingDTO> findAll(Pageable pageable);

    BookingDTO addBooking(@Valid CreateBookingDTO bookingDTO);

    BookingDTO findBookingById(Long id);

    BookingDTO updateBooking(Long id, @Valid UpdateBookingDTO createUpdateBookingDTO);

    void deleteBooking(Long id);
}
