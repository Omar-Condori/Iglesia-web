package com.iglesia.adventistas.modules.homevisits.entity;

import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import com.iglesia.adventistas.modules.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_visit_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeVisitRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Sección 1: Datos personales
    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_member", length = 50)
    private MembershipStatus isMember;

    // Sección 2: Contacto
    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 200)
    private String email;

    // Sección 3: Dirección
    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "additional_reference", columnDefinition = "TEXT")
    private String additionalReference;

    // Sección 4: Motivo
    @Enumerated(EnumType.STRING)
    @Column(name = "visit_reason", nullable = false, length = 100)
    private VisitReason visitReason;

    @Column(name = "custom_reason", columnDefinition = "TEXT")
    private String customReason; // Si eligió "Otro"

    // Sección 5: A quién visitar
    @Enumerated(EnumType.STRING)
    @Column(name = "visit_target", nullable = false, length = 50)
    private VisitTarget visitTarget;

    // Sección 6: Horarios
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_time", nullable = false, length = 50)
    private PreferredTime preferredTime;

    @Column(name = "preferred_date")
    private LocalDate preferredDate;

    // Sección 7: Tipo de visita
    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type", nullable = false, length = 50)
    private VisitType visitType;

    // Sección 8: Privacidad
    @Enumerated(EnumType.STRING)
    @Column(name = "privacy_level", nullable = false, length = 50)
    private PrivacyLevel privacyLevel;

    // Sección 9: Notificaciones
    @Column(name = "wants_notifications")
    @Builder.Default
    private Boolean wantsNotifications = false;

    // Estado
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attended_by")
    private User attendedBy;

    @Column(name = "attended_at")
    private LocalDateTime attendedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
