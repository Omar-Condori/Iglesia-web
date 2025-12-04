package com.iglesia.adventistas.modules.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String thumbnailUrl;
    private String level;
    private Integer durationMinutes;
    private Boolean isActive;
    private Boolean isFeatured;
    private Integer enrollmentsCount;
    private Integer lessonsCount;
    private String categoryName;
    private String instructorName;
    private LocalDateTime createdAt;
}