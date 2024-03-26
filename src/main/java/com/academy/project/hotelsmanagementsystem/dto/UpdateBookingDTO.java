package com.academy.project.hotelsmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingDTO {
    //@NotNull
    private Long id;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private Integer totalNumGuests;

    private String specialReq;

}
