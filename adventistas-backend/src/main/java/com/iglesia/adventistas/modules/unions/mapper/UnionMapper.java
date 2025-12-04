package com.iglesia.adventistas.modules.unions.mapper;

import com.iglesia.adventistas.modules.unions.dto.CreateUnionRequest;
import com.iglesia.adventistas.modules.unions.dto.UnionDTO;
import com.iglesia.adventistas.modules.unions.entity.Union;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

@Component
public class UnionMapper {

    public UnionDTO toDTO(Union union) {
        if (union == null) return null;

        return UnionDTO.builder()
                .id(union.getId())
                .name(union.getName())
                .slug(union.getSlug())
                .description(union.getDescription())
                .address(union.getAddress())
                .city(union.getCity())
                .state(union.getState())
                .country(union.getCountry())
                .phone(union.getPhone())
                .email(union.getEmail())
                .website(union.getWebsite())
                .logoUrl(null) // No existe en entity
                .isActive(union.getIsActive())
                .createdAt(union.getCreatedAt())
                .build();
    }

    public Union toEntity(CreateUnionRequest request) {
        if (request == null) return null;

        Union union = new Union();
        union.setName(request.getName());
        union.setSlug(SlugUtils.generateSlug(request.getName()));
        union.setDescription(request.getDescription());
        union.setAddress(request.getAddress());
        union.setCity(request.getCity());
        union.setState(request.getState());
        union.setCountry(request.getCountry());
        union.setPhone(request.getPhone());
        union.setEmail(request.getEmail());
        union.setWebsite(request.getWebsite());
        // logoUrl no existe en entity
        union.setIsActive(true);

        return union;
    }

    public void updateEntity(CreateUnionRequest request, Union union) {
        if (request == null || union == null) return;

        union.setName(request.getName());
        union.setSlug(SlugUtils.generateSlug(request.getName()));
        union.setDescription(request.getDescription());
        union.setAddress(request.getAddress());
        union.setCity(request.getCity());
        union.setState(request.getState());
        union.setCountry(request.getCountry());
        union.setPhone(request.getPhone());
        union.setEmail(request.getEmail());
        union.setWebsite(request.getWebsite());
        // logoUrl no existe en entity
    }
}