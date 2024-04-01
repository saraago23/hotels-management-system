package com.academy.project.hotelsmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookedDTO {

    //@NotNull(message = "{roomBooked.validations.id}")
    private Long id;
    @NotNull(message = "{roomBooked.validations.room}")
    private RoomDTO room;
    @NotNull(message = "{roomBooked.validations.booking}")
    private BookingDTO booking;
    @NotNull(message = "{roomBooked.validations.price}")
    private BigDecimal price;
    @JsonIgnore
    private Boolean deleted;
}
