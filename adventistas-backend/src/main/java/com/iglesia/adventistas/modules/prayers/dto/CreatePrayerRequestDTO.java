package com.iglesia.adventistas.modules.prayers.dto;

import com.iglesia.adventistas.modules.prayers.entity.PreferenceType;
import com.iglesia.adventistas.modules.prayers.entity.Urgency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePrayerRequestDTO {

    @NotBlank(message = "El número de celular es requerido")
    @Size(max = 20, message = "El número de celular no debe exceder 20 caracteres")
    private String phone;

    @NotBlank(message = "El distrito es requerido")
    @Size(max = 100, message = "El distrito no debe exceder 100 caracteres")
    private String district;

    @NotNull(message = "El tipo de preferencia es requerido")
    private PreferenceType preferenceType;

    @NotNull(message = "La urgencia es requerida")
    private Urgency urgency;

    private String message;

    @Builder.Default
    private Boolean wantsNotifications = false;
}
