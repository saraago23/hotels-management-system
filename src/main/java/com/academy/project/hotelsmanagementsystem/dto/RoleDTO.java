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
public class RoleDTO {

    @NotNull(message = "{role.validations.id}")
    private Integer id;
    @NotNull(message = "{role.validations.title}")
    private String title;
    private String description;

}
