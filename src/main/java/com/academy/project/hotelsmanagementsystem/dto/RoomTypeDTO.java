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
public class RoomTypeDTO {
    //@NotNull(message = "{roomType.validations.id}")
    private Long id;
    @NotNull(message = "{roomType.validations.type}")
    private String type;
    @NotNull(message = "{roomType.validations.price}")
    private Integer price;

    private String roomDesc;

}
