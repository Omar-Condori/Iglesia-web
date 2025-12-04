package com.iglesia.adventistas.modules.videos.service;

import com.iglesia.adventistas.modules.videos.dto.CreateVideoRequest;
import com.iglesia.adventistas.modules.videos.dto.VideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {

    VideoDTO createVideo(CreateVideoRequest request, Long uploaderId);

    VideoDTO updateVideo(Long id, CreateVideoRequest request);

    VideoDTO getVideoById(Long id);

    VideoDTO getVideoBySlug(String slug);

    Page<VideoDTO> getAllVideos(Pageable pageable);

    Page<VideoDTO> getActiveVideos(Pageable pageable);

    Page<VideoDTO> getVideosByCategory(Long categoryId, Pageable pageable);

    void deleteVideo(Long id);

    void incrementViewCount(Long id);
}