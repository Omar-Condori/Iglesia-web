package com.iglesia.adventistas.modules.downloads.dto;

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
public class CreateDownloadRequest {

    @NotBlank(message = "El título es requerido")
    private String title;

    private String description;

    @NotBlank(message = "La URL del archivo es requerida")
    private String fileUrl;

    @NotBlank(message = "El tipo de archivo es requerido")
    private String fileType;

    @NotNull(message = "El tamaño del archivo es requerido")
    private Long fileSize;

    @NotNull(message = "La categoría es requerida")
    private Long categoryId;

    private Long departmentId;
}