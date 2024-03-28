package com.academy.project.hotelsmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDTO {
    //@NotNull
    private Long id;
    @NotNull
    private LocalDateTime checkInTime;
    @NotNull
    private LocalDateTime checkOutTime;

    private Integer totalNumGuests;

    private String specialReq;

    private List<RoomDTO> roomDTOList;

}
