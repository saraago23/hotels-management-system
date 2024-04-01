package com.academy.project.hotelsmanagementsystem.dto;

import com.academy.project.hotelsmanagementsystem.annotations.AdultAge;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    //@NotNull(message = "{user.validations.id}")
    private Long id;
    private String firstName;
    private String lastName;
    @Past(message = "Birthdate must be in the past")
    @AdultAge
    private LocalDate birthDate;
    private char gender;
    private String phone;
    private String email;
    private String address;
    @NotNull(message = "{user.validation.role}")
    private RoleDTO role;
    @NotNull(message = "{user.validation.username}")
    private String username;
    @NotNull(message = "{user.validation.password}")
    private String password;
    @JsonIgnore
    private Boolean deleted;

    public UserDTO(String email, String password) {
        this.email=email;
        this.password=password;
    }
}
