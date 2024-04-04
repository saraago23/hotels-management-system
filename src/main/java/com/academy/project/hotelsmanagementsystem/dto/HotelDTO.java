package com.academy.project.hotelsmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDTO {
    //@NotNull(message = "{hotel.validations.id}")
    private Long id;
    @NotNull(message = "{hotel.validations.hotelName}")
    private String hotelName;
    private String address;
    private Integer postCode;
    @NotNull(message = "{hotel.validations.city}")
    private String city;
    private String country;
    @NotNull(message = "{hotel.validations.roomsNr}")
    private Integer roomsNr;
    @NotNull(message = "{hotel.validations.phone}")
    //@Pattern(regexp = "^\\+355\\s(68|69)\\d{7}$\n",message = "{hotel.validations.phone.regexp}")
    private String phone;
    private BigDecimal starRating;
    @JsonIgnore
    private Boolean deleted;
}
