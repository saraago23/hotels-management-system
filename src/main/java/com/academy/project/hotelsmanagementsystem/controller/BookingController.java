package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.exceptions.RecordNotFoundException;
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
       var emp=bookingService.findBookingById(id)
               .orElseThrow(()-> new RecordNotFoundException("Booking with id: " + id + " was not found"));
        return ResponseEntity.ok(emp);

    }
    @PostMapping
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingDTO booking){
       return ResponseEntity.ok(bookingService.addBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id,@RequestBody BookingDTO booking){
        return ResponseEntity.ok(bookingService.updateBooking(id,booking));
    }
}
