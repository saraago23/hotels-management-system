package com.academy.project.hotelsmanagementsystem.dto;

import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
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
    @NotNull(message = "{room.validations.id}")
    private Integer id;
    @NotNull(message = "{room.validations.roomType}")
    private RoomTypeEntity roomType;
    @NotNull(message = "{room.validations.hotel}")
    private HotelEntity hotel;
    @NotNull(message = "{room.validations.isBooked}")
    private boolean isBooked;

}
