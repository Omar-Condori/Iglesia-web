package com.iglesia.adventistas.modules.homevisits.mapper;

import com.iglesia.adventistas.modules.homevisits.dto.CreateHomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.dto.HomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.entity.HomeVisitRequest;
import org.springframework.stereotype.Component;

@Component
public class HomeVisitRequestMapper {

    public HomeVisitRequestDTO toDTO(HomeVisitRequest request) {
        if (request == null)
            return null;

        return HomeVisitRequestDTO.builder()
                .id(request.getId())
                .userId(request.getUser().getId())
                .userName(request.getUser().getFirstName() + " " + request.getUser().getLastName())
                .userEmail(request.getUser().getEmail())
                .fullName(request.getFullName())
                .isMember(request.getIsMember())
                .phone(request.getPhone())
                .email(request.getEmail())
                .district(request.getDistrict())
                .address(request.getAddress())
                .additionalReference(request.getAdditionalReference())
                .visitReason(request.getVisitReason())
                .customReason(request.getCustomReason())
                .visitTarget(request.getVisitTarget())
                .preferredTime(request.getPreferredTime())
                .preferredDate(request.getPreferredDate())
                .visitType(request.getVisitType())
                .privacyLevel(request.getPrivacyLevel())
                .wantsNotifications(request.getWantsNotifications())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .attendedByName(request.getAttendedBy() != null
                        ? request.getAttendedBy().getFirstName() + " " + request.getAttendedBy().getLastName()
                        : null)
                .attendedAt(request.getAttendedAt())
                .build();
    }

    public HomeVisitRequest toEntity(CreateHomeVisitRequestDTO dto) {
        if (dto == null)
            return null;

        return HomeVisitRequest.builder()
                .fullName(dto.getFullName())
                .isMember(dto.getIsMember())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .district(dto.getDistrict())
                .address(dto.getAddress())
                .additionalReference(dto.getAdditionalReference())
                .visitReason(dto.getVisitReason())
                .customReason(dto.getCustomReason())
                .visitTarget(dto.getVisitTarget())
                .preferredTime(dto.getPreferredTime())
                .preferredDate(dto.getPreferredDate())
                .visitType(dto.getVisitType())
                .privacyLevel(dto.getPrivacyLevel())
                .wantsNotifications(dto.getWantsNotifications() != null ? dto.getWantsNotifications() : false)
                .build();
    }
}
