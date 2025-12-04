package com.iglesia.adventistas.modules.churches.mapper;

import com.iglesia.adventistas.modules.churches.dto.ChurchDTO;
import com.iglesia.adventistas.modules.churches.dto.CreateChurchRequest;
import com.iglesia.adventistas.modules.churches.entity.Church;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChurchMapper {

    public ChurchDTO toDTO(Church church) {
        if (church == null) return null;

        return ChurchDTO.builder()
                .id(church.getId())
                .name(church.getName())
                .slug(church.getSlug())
                .description(church.getDescription())
                .address(church.getAddress())
                .city(church.getCity())
                .state(church.getState())
                .postalCode(church.getPostalCode())
                .country(church.getCountry())
                .phone(church.getPhone())
                .email(church.getEmail())
                .website(church.getWebsite())
                .latitude(church.getLatitude() != null ? church.getLatitude().doubleValue() : null)
                .longitude(church.getLongitude() != null ? church.getLongitude().doubleValue() : null)
                .isActive(church.getIsActive())
                .unionName(church.getUnion() != null ? church.getUnion().getName() : null)
                .createdAt(church.getCreatedAt())
                .build();
    }

    public Church toEntity(CreateChurchRequest request) {
        if (request == null) return null;

        Church church = new Church();
        church.setName(request.getName());
        church.setSlug(SlugUtils.generateSlug(request.getName()));
        church.setDescription(request.getDescription());
        church.setAddress(request.getAddress());
        church.setCity(request.getCity());
        church.setState(request.getState());
        church.setPostalCode(request.getPostalCode());
        church.setCountry(request.getCountry());
        church.setPhone(request.getPhone());
        church.setEmail(request.getEmail());
        church.setWebsite(request.getWebsite());
        church.setLatitude(request.getLatitude() != null ? BigDecimal.valueOf(request.getLatitude()) : null);
        church.setLongitude(request.getLongitude() != null ? BigDecimal.valueOf(request.getLongitude()) : null);
        church.setIsActive(true);

        return church;
    }

    public void updateEntity(CreateChurchRequest request, Church church) {
        if (request == null || church == null) return;

        church.setName(request.getName());
        church.setSlug(SlugUtils.generateSlug(request.getName()));
        church.setDescription(request.getDescription());
        church.setAddress(request.getAddress());
        church.setCity(request.getCity());
        church.setState(request.getState());
        church.setPostalCode(request.getPostalCode());
        church.setCountry(request.getCountry());
        church.setPhone(request.getPhone());
        church.setEmail(request.getEmail());
        church.setWebsite(request.getWebsite());
        church.setLatitude(request.getLatitude() != null ? BigDecimal.valueOf(request.getLatitude()) : null);
        church.setLongitude(request.getLongitude() != null ? BigDecimal.valueOf(request.getLongitude()) : null);
    }
}