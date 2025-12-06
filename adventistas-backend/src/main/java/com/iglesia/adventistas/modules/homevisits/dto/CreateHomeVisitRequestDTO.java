package com.iglesia.adventistas.modules.homevisits.dto;

import com.iglesia.adventistas.modules.homevisits.entity.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHomeVisitRequestDTO {

    // Sección 1
    @NotBlank(message = "El nombre completo es requerido")
    @Size(max = 200)
    private String fullName;

    private MembershipStatus isMember;

    // Sección 2
    @NotBlank(message = "El número de celular es requerido")
    @Size(max = 20)
    private String phone;

    @Email(message = "Email inválido")
    @Size(max = 200)
    private String email;

    // Sección 3
    @NotBlank(message = "El distrito es requerido")
    @Size(max = 100)
    private String district;

    @NotBlank(message = "La dirección es requerida")
    private String address;

    private String additionalReference;

    // Sección 4
    @NotNull(message = "El motivo de la visita es requerido")
    private VisitReason visitReason;

    private String customReason;

    // Sección 5
    @NotNull(message = "Debe especificar a quién visitar")
    private VisitTarget visitTarget;

    // Sección 6
    @NotNull(message = "El horario preferido es requerido")
    private PreferredTime preferredTime;

    private LocalDate preferredDate;

    // Sección 7
    @NotNull(message = "El tipo de visita es requerido")
    private VisitType visitType;

    // Sección 8
    @NotNull(message = "El nivel de privacidad es requerido")
    private PrivacyLevel privacyLevel;

    // Sección 9
    @Builder.Default
    private Boolean wantsNotifications = false;
}
