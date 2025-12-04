package com.iglesia.adventistas.modules.departments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String color;
    private String icon;
    private Boolean isActive;
    private Integer sortOrder;
    private LocalDateTime createdAt;
}