package com.iglesia.adventistas.modules.departments.mapper;

import com.iglesia.adventistas.modules.departments.dto.CreateDepartmentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentDTO;
import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.shared.util.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {

    private final DepartmentSectionMapper sectionMapper;

    public DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        DepartmentDTO dto = DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .slug(department.getSlug())
                .description(department.getDescription())
                .color(department.getColor())
                .icon(department.getIcon())
                .isActive(department.getIsActive())
                .sortOrder(department.getSortOrder())
                .createdAt(department.getCreatedAt())
                .build();

        // Map sections if they are loaded
        if (department.getSections() != null && !department.getSections().isEmpty()) {
            dto.setSections(department.getSections().stream()
                    .map(sectionMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Department toEntity(CreateDepartmentRequest request) {
        if (request == null)
            return null;

        Department department = new Department();
        department.setName(request.getName());
        department.setSlug(SlugUtils.generateSlug(request.getName()));
        department.setDescription(request.getDescription());
        department.setColor(request.getColor());
        department.setIcon(request.getIcon());
        department.setIsActive(true);
        department.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);

        return department;
    }

    public void updateEntity(CreateDepartmentRequest request, Department department) {
        if (request == null || department == null)
            return;

        department.setName(request.getName());
        department.setSlug(SlugUtils.generateSlug(request.getName()));
        department.setDescription(request.getDescription());
        department.setColor(request.getColor());
        department.setIcon(request.getIcon());
        if (request.getSortOrder() != null) {
            department.setSortOrder(request.getSortOrder());
        }
    }
}