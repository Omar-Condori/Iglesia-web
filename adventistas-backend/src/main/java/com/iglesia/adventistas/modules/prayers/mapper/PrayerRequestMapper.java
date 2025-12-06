package com.iglesia.adventistas.modules.prayers.mapper;

import com.iglesia.adventistas.modules.prayers.dto.CreatePrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.dto.PrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.entity.PrayerRequest;
import org.springframework.stereotype.Component;

@Component
public class PrayerRequestMapper {

    public PrayerRequestDTO toDTO(PrayerRequest request) {
        if (request == null)
            return null;

        return PrayerRequestDTO.builder()
                .id(request.getId())
                .userId(request.getUser().getId())
                .userName(request.getUser().getFirstName() + " " + request.getUser().getLastName())
                .userEmail(request.getUser().getEmail())
                .phone(request.getPhone())
                .district(request.getDistrict())
                .preferenceType(request.getPreferenceType())
                .urgency(request.getUrgency())
                .message(request.getMessage())
                .status(request.getStatus())
                .wantsNotifications(request.getWantsNotifications())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .attendedByName(request.getAttendedBy() != null
                        ? request.getAttendedBy().getFirstName() + " " + request.getAttendedBy().getLastName()
                        : null)
                .attendedAt(request.getAttendedAt())
                .build();
    }

    public PrayerRequest toEntity(CreatePrayerRequestDTO dto) {
        if (dto == null)
            return null;

        return PrayerRequest.builder()
                .phone(dto.getPhone())
                .district(dto.getDistrict())
                .preferenceType(dto.getPreferenceType())
                .urgency(dto.getUrgency())
                .message(dto.getMessage())
                .wantsNotifications(dto.getWantsNotifications() != null ? dto.getWantsNotifications() : false)
                .build();
    }
}
