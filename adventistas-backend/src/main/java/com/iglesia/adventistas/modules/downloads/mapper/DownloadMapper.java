package com.iglesia.adventistas.modules.downloads.mapper;

import com.iglesia.adventistas.modules.downloads.dto.CreateDownloadRequest;
import com.iglesia.adventistas.modules.downloads.dto.DownloadDTO;
import com.iglesia.adventistas.modules.downloads.entity.Download;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

@Component
public class DownloadMapper {

    public DownloadDTO toDTO(Download download) {
        if (download == null) return null;

        return DownloadDTO.builder()
                .id(download.getId())
                .title(download.getTitle())
                .slug(download.getSlug())
                .description(download.getDescription())
                .fileUrl(download.getFileUrl())
                .fileType(download.getFileType())
                .fileSize(download.getFileSize())
                .downloadsCount(download.getDownloadsCount())
                .isActive(download.getIsActive())
                .categoryName(download.getCategory() != null ? download.getCategory().getName() : null)
                .departmentName(download.getDepartment() != null ? download.getDepartment().getName() : null)
                .uploaderName(download.getUploadedBy() != null ? download.getUploadedBy().getFullName() : null)
                .createdAt(download.getCreatedAt())
                .build();
    }

    public Download toEntity(CreateDownloadRequest request) {
        if (request == null) return null;

        Download download = new Download();
        download.setTitle(request.getTitle());
        download.setSlug(SlugUtils.generateSlug(request.getTitle()));
        download.setDescription(request.getDescription());
        download.setFileUrl(request.getFileUrl());
        download.setFileType(request.getFileType());
        download.setFileSize(request.getFileSize());
        download.setDownloadsCount(0);
        download.setIsActive(true);

        return download;
    }

    public void updateEntity(CreateDownloadRequest request, Download download) {
        if (request == null || download == null) return;

        download.setTitle(request.getTitle());
        download.setSlug(SlugUtils.generateSlug(request.getTitle()));
        download.setDescription(request.getDescription());
        download.setFileUrl(request.getFileUrl());
        download.setFileType(request.getFileType());
        download.setFileSize(request.getFileSize());
    }
}