package com.iglesia.adventistas.modules.lessons.dto;

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
public class CreateLessonRequest {

    @NotBlank(message = "El título es requerido")
    private String title;

    private String description;
    private String content;
    private String videoUrl;
    private Integer durationMinutes;
    private Integer sortOrder;
    private Boolean isFree;

    @NotNull(message = "El curso es requerido")
    private Long courseId;
}