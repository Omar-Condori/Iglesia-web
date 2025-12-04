package com.iglesia.adventistas.modules.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Long id;
    private String title;
    private String slug;
    private String summary;
    private String content;
    private String featuredImage;
    private String status;
    private Boolean isFeatured;
    private Integer viewsCount;
    private LocalDateTime publishedAt;
    private String authorName;
    private String categoryName;
    private String departmentName;
    private LocalDateTime createdAt;
}