package com.iglesia.adventistas.modules.homevisits.dto;

import com.iglesia.adventistas.modules.homevisits.entity.*;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeVisitRequestDTO {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;

    // Datos personales
    private String fullName;
    private MembershipStatus isMember;

    // Contacto
    private String phone;
    private String email;

    // Dirección
    private String district;
    private String address;
    private String additionalReference;

    // Motivo
    private VisitReason visitReason;
    private String customReason;

    // A quién visitar
    private VisitTarget visitTarget;

    // Horarios
    private PreferredTime preferredTime;
    private LocalDate preferredDate;

    // Tipo visita
    private VisitType visitType;

    // Privacidad
    private PrivacyLevel privacyLevel;

    // Notificaciones
    private Boolean wantsNotifications;

    // Estado
    private RequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String attendedByName;
    private LocalDateTime attendedAt;
}
