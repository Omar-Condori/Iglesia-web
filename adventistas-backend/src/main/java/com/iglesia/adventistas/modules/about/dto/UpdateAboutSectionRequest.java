package com.iglesia.adventistas.modules.about.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAboutSectionRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;

    private String content;

    @Size(max = 50, message = "El ícono no puede exceder 50 caracteres")
    private String icon;

    private Integer sortOrder;

    private Boolean isActive;
}
