package com.iglesia.adventistas.modules.videos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private String platform;
    private Integer durationSeconds;
    private Integer viewsCount;
    private Boolean isActive;
    private String categoryName;
    private String departmentName;
    private String uploaderName;
    private LocalDateTime createdAt;
}