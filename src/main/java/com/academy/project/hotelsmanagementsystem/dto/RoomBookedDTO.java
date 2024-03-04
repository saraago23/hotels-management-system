package com.academy.project.hotelsmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookedDTO {

    @NotNull(message = "{roomBooked.validations.id}")
    private Integer id;
    @NotNull(message = "{roomBooked.validations.room}")
    private RoomDTO room;
    @NotNull(message = "{roomBooked.validations.booking}")
    private BookingDTO booking;
    @NotNull(message = "{roomBooked.validations.price}")
    private Integer price;
}
