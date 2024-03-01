package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.exceptions.RecordNotFoundException;
import com.academy.project.hotelsmanagementsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<PageDTO<BookingDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {

        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findBookingById(@PathVariable Integer id) {
       var emp=bookingService.findBookingById(id)
               .orElseThrow(()-> new RecordNotFoundException("Booking with id: " + id + " was not found"));
        return ResponseEntity.ok(emp);

    }
}
