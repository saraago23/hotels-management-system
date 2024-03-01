package com.academy.project.hotelsmanagementsystem.dto;

import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import com.academy.project.hotelsmanagementsystem.entity.PersonEntity;
import com.academy.project.hotelsmanagementsystem.entity.ProfessionEntity;
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
    private PersonEntity person;
    @NotNull(message = "{employee.validations.hotel}")
    private HotelEntity hotel;
    private ProfessionEntity profession;
    private Integer salary;

}
