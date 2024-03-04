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
public class UserDTO {
    @NotNull(message = "{user.validations.id}")
    private Integer id;
    @NotNull(message = "{user.validation.person}")
    private PersonDTO person;
    @NotNull(message = "{user.validation.role}")
    private RoleDTO role;
    @NotNull(message = "{user.validation.username}")
    private String username;
    @NotNull(message = "{user.validation.password}")
    private String password;

}
