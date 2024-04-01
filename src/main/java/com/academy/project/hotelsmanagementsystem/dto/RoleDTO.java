package com.academy.project.hotelsmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // @NotNull(message = "{role.validations.id}")
    private Long id;
    @NotNull(message = "{role.validations.title}")
    private String title;
    private String description;
    @JsonIgnore

    private Boolean deleted;
}
