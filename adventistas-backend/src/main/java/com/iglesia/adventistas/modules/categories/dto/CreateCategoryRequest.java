package com.iglesia.adventistas.modules.categories.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    private String description;
    private String color;
    private Integer sortOrder;
}