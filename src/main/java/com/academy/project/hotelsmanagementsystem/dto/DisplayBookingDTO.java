package com.academy.project.hotelsmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayBookingDTO {

    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Integer totalNumGuests;
    private String specialReq;
    private List<RoomDTO> rooms;

}
