package com.academy.project.hotelsmanagementsystem.dto;

import com.academy.project.hotelsmanagementsystem.entity.PersonEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
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
    private PersonEntity person;
    @NotNull(message = "{user.validation.role}")
    private RoleEntity role;
    @NotNull(message = "{user.validation.username}")
    private String username;
    @NotNull(message = "{user.validation.password}")
    private String password;

}
