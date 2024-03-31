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
public class RoomTypeDTO {
    //@NotNull(message = "{roomType.validations.id}")
    private Long id;
    @NotNull(message = "{roomType.validations.type}")
    private String type;
    @NotNull(message = "{roomType.validations.price}")
    private BigDecimal price;
    private Integer numGuest;
    private String roomDesc;
    @JsonIgnore
    private Boolean deleted;

}
