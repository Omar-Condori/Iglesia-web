package com.iglesia.adventistas.modules.departments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSectionDTO {
    private Long id;
    private Long departmentId;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private Integer sortOrder;
    private Boolean isActive;
}
