package com.academy.project.hotelsmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    // @NotNull(message = "{room.validations.id}")
    private Long id;
    @NotNull(message = "{room.validations.roomNr}")
    private Integer roomNr;
    @NotNull(message = "{room.validations.roomType}")
    private RoomTypeDTO roomType;
    @NotNull(message = "{room.validations.hotel}")
    private HotelDTO hotel;
    @JsonIgnore
    private Boolean deleted;

}
