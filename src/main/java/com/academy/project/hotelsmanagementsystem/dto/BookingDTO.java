package com.academy.project.hotelsmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    @NotNull
    private Integer id;
    @NotNull
    private UserDTO user;
    @NotNull
    private Integer totalPrice;
    @NotNull
    private LocalDateTime bookingTime;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private Integer numAdults;

    private Integer numChildren;

    private String specialReq;

}
