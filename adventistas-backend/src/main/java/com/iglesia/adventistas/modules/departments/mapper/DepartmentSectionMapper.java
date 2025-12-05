package com.iglesia.adventistas.modules.departments.mapper;

import com.iglesia.adventistas.modules.departments.dto.DepartmentSectionDTO;
import com.iglesia.adventistas.modules.departments.entity.DepartmentSection;
import org.springframework.stereotype.Component;

@Component
public class DepartmentSectionMapper {

    public DepartmentSectionDTO toDTO(DepartmentSection section) {
        if (section == null) {
            return null;
        }

        return DepartmentSectionDTO.builder()
                .id(section.getId())
                .departmentId(section.getDepartment().getId())
                .name(section.getName())
                .slug(section.getSlug())
                .description(section.getDescription())
                .icon(section.getIcon())
                .sortOrder(section.getSortOrder())
                .isActive(section.getIsActive())
                .build();
    }
}
