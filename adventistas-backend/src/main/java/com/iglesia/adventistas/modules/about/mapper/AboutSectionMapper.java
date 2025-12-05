package com.iglesia.adventistas.modules.about.mapper;

import com.iglesia.adventistas.modules.about.dto.AboutSectionDTO;
import com.iglesia.adventistas.modules.about.entity.AboutSection;
import org.springframework.stereotype.Component;

@Component
public class AboutSectionMapper {

    public AboutSectionDTO toDTO(AboutSection entity) {
        if (entity == null) {
            return null;
        }

        return AboutSectionDTO.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .content(entity.getContent())
                .icon(entity.getIcon())
                .sortOrder(entity.getSortOrder())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
