package com.iglesia.adventistas.modules.departments.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContentRequest {

    @NotBlank(message = "El título es requerido")
    private String title;

    @NotBlank(message = "El contenido es requerido")
    private String content;

    private String excerpt;
    private String featuredImage;
    private Boolean isPublished = false;
    private Integer sortOrder = 0;
}
