package com.iglesia.adventistas.modules.prayers.dto;

import com.iglesia.adventistas.modules.prayers.entity.PreferenceType;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import com.iglesia.adventistas.modules.prayers.entity.Urgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayerRequestDTO {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String phone;
    private String district;
    private PreferenceType preferenceType;
    private Urgency urgency;
    private String message;
    private RequestStatus status;
    private Boolean wantsNotifications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String attendedByName;
    private LocalDateTime attendedAt;
}
