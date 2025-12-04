package com.iglesia.adventistas.modules.lessons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String content;
    private String videoUrl;
    private Integer durationMinutes;
    private Integer sortOrder;
    private Boolean isActive;
    private Boolean isFree;
    private String courseTitle;
    private LocalDateTime createdAt;
}