package com.iglesia.adventistas.modules.courses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {

    @NotBlank(message = "El título es requerido")
    private String title;

    private String description;
    private String thumbnailUrl;

    @NotBlank(message = "El nivel es requerido")
    private String level;

    private Integer durationMinutes;

    @NotNull(message = "La categoría es requerida")
    private Long categoryId;

    @NotNull(message = "El instructor es requerido")
    private Long instructorId;
}