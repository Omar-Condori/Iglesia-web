package com.iglesia.adventistas.modules.about.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutSectionDTO {
    private Long id;
    private String slug;
    private String title;
    private String content;
    private String icon;
    private Integer sortOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
