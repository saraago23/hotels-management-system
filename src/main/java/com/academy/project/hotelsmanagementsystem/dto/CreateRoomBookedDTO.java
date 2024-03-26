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
public class CreateRoomBookedDTO {

    //@NotNull(message = "{roomBooked.validations.id}")
    private Long id;
    @NotNull(message = "{roomBooked.validations.room}")
    private Long roomId;
    @NotNull(message = "{roomBooked.validations.booking}")
    private Long bookingId;
    @NotNull(message = "{roomBooked.validations.price}")
    private Integer price;
}
