package com.academy.project.hotelsmanagementsystem.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotNull(message = "{person.validations.id}")
    private Integer id;
    @NotNull(message = "{person.validations.firstName}")
    private String firstName;
    @NotNull(message = "{person.validations.lastName}")
    private String lastName;
    @NotNull(message = "{person.validations.birthDate}")
    private LocalDate birthDate;
    @NotNull(message = "{person.validations.gender}")
    private char gender;
    @Pattern(regexp = "^\\+355\\s(68|69)\\d{7}$\n",message = "{person.validations.phone.regexp}")
    private String phone;
    @NotNull(message = "{person.validations.email}")
    private String email;
    private String address;


}

