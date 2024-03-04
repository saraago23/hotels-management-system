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
public class EmployeeDTO {

    @NotNull(message = "{employee.validations.id}")
    private Integer id;
    @NotNull(message = "{employee.validations.person}")
    private PersonDTO person;
    @NotNull(message = "{employee.validations.hotel}")
    private HotelDTO hotel;
    private ProfessionDTO profession;
    private Integer salary;

}
