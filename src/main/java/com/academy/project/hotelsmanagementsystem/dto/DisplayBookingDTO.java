package com.academy.project.hotelsmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayBookingDTO {
    //@NotNull
    private Long id;
    @NotNull
    private UserDTO user;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private LocalDateTime bookingTime;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private Integer totalNumGuests;

    private String specialReq;

}
