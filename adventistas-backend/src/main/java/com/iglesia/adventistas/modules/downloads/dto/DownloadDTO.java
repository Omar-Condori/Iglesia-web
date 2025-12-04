package com.iglesia.adventistas.modules.downloads.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
    private Integer downloadsCount;
    private Boolean isActive;
    private String categoryName;
    private String departmentName;
    private String uploaderName;
    private LocalDateTime createdAt;
}