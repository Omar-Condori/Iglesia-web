package com.iglesia.adventistas.modules.news.dto;

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
public class CreateNewsRequest {

    @NotBlank(message = "El título es requerido")
    private String title;

    private String summary;

    @NotBlank(message = "El contenido es requerido")
    private String content;

    private String featuredImage;

    @NotNull(message = "La categoría es requerida")
    private Long categoryId;

    private Long departmentId;

    private Boolean isFeatured = false;
    private Boolean allowComments = true;
}