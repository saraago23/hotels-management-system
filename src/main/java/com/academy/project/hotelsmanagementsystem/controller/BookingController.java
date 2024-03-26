package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.CreateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.UpdateBookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.service.BookingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<BookingDTO>> getBookings(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {

        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findBookingById(id));

    }
    @PostMapping
    public ResponseEntity<BookingDTO> addBooking(@RequestBody CreateBookingDTO booking){
       return ResponseEntity.ok(bookingService.addBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody UpdateBookingDTO createUpdateBookingDTO){
        return ResponseEntity.ok(bookingService.updateBooking(id, createUpdateBookingDTO));
    }

}
