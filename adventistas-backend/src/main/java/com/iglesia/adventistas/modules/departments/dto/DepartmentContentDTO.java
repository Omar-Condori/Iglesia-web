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
public class DepartmentContentDTO {
    private Long id;
    private Long sectionId;
    private String sectionName;
    private String departmentName;
    private String title;
    private String slug;
    private String content;
    private String excerpt;
    private String featuredImage;
    private String authorEmail;
    private Long authorId;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private Integer sortOrder;
    private Integer views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
