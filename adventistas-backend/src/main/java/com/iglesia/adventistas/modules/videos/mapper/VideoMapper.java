package com.iglesia.adventistas.modules.videos.mapper;

import com.iglesia.adventistas.modules.videos.dto.CreateVideoRequest;
import com.iglesia.adventistas.modules.videos.dto.VideoDTO;
import com.iglesia.adventistas.modules.videos.entity.Video;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public VideoDTO toDTO(Video video) {
        if (video == null) return null;

        return VideoDTO.builder()
                .id(video.getId())
                .title(video.getTitle())
                .slug(video.getSlug())
                .description(video.getDescription())
                .videoUrl(video.getVideoUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .platform(video.getPlatform())
                .durationSeconds(video.getDurationSeconds())
                .viewsCount(video.getViewsCount())
                .isActive(video.getIsActive())
                .categoryName(video.getCategory() != null ? video.getCategory().getName() : null)
                .departmentName(video.getDepartment() != null ? video.getDepartment().getName() : null)
                .uploaderName(video.getUploadedBy() != null ? video.getUploadedBy().getFullName() : null)
                .createdAt(video.getCreatedAt())
                .build();
    }

    public Video toEntity(CreateVideoRequest request) {
        if (request == null) return null;

        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setSlug(SlugUtils.generateSlug(request.getTitle()));
        video.setDescription(request.getDescription());
        video.setVideoUrl(request.getVideoUrl());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setPlatform(request.getPlatform());
        video.setDurationSeconds(request.getDurationSeconds());
        video.setViewsCount(0);
        video.setIsActive(true);

        return video;
    }

    public void updateEntity(CreateVideoRequest request, Video video) {
        if (request == null || video == null) return;

        video.setTitle(request.getTitle());
        video.setSlug(SlugUtils.generateSlug(request.getTitle()));
        video.setDescription(request.getDescription());
        video.setVideoUrl(request.getVideoUrl());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setPlatform(request.getPlatform());
        video.setDurationSeconds(request.getDurationSeconds());
    }
}