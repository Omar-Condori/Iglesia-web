package com.iglesia.adventistas.modules.prayers.entity;

import com.iglesia.adventistas.modules.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "prayer_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String district;

    @Enumerated(EnumType.STRING)
    @Column(name = "preference_type", nullable = false, length = 50)
    private PreferenceType preferenceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Urgency urgency;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "wants_notifications")
    @Builder.Default
    private Boolean wantsNotifications = false;

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
