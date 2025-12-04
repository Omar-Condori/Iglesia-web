package com.iglesia.adventistas.modules.videos.dto;

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
public class CreateVideoRequest {

    @NotBlank(message = "El título es requerido")
    private String title;

    private String description;

    @NotBlank(message = "La URL del video es requerida")
    private String videoUrl;

    private String thumbnailUrl;

    @NotBlank(message = "La plataforma es requerida")
    private String platform;

    private Integer durationSeconds;

    @NotNull(message = "La categoría es requerida")
    private Long categoryId;

    private Long departmentId;
}