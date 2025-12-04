package com.iglesia.adventistas.modules.videos.service;

import com.iglesia.adventistas.modules.departments.entity.Department;
import com.iglesia.adventistas.modules.departments.repository.DepartmentRepository;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.modules.videos.dto.CreateVideoRequest;
import com.iglesia.adventistas.modules.videos.dto.VideoDTO;
import com.iglesia.adventistas.modules.videos.entity.Video;
import com.iglesia.adventistas.modules.videos.entity.VideoCategory;
import com.iglesia.adventistas.modules.videos.mapper.VideoMapper;
import com.iglesia.adventistas.modules.videos.repository.VideoCategoryRepository;
import com.iglesia.adventistas.modules.videos.repository.VideoRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoCategoryRepository videoCategoryRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;

    @Override
    public VideoDTO createVideo(CreateVideoRequest request, Long uploaderId) {
        log.info("Creating video: {}", request.getTitle());

        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        VideoCategory category = videoCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Video video = videoMapper.toEntity(request);
        video.setCategory(category);
        video.setUploadedBy(uploader);

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado"));
            video.setDepartment(department);
        }

        Video saved = videoRepository.save(video);
        return videoMapper.toDTO(saved);
    }

    @Override
    public VideoDTO updateVideo(Long id, CreateVideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));

        videoMapper.updateEntity(request, video);

        if (!video.getCategory().getId().equals(request.getCategoryId())) {
            VideoCategory category = videoCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            video.setCategory(category);
        }

        Video updated = videoRepository.save(video);
        return videoMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public VideoDTO getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));

        return videoMapper.toDTO(video);
    }

    @Override
    @Transactional(readOnly = true)
    public VideoDTO getVideoBySlug(String slug) {
        Video video = videoRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));

        return videoMapper.toDTO(video);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VideoDTO> getAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable)
                .map(videoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VideoDTO> getActiveVideos(Pageable pageable) {
        return videoRepository.findByIsActiveTrue(pageable)
                .map(videoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VideoDTO> getVideosByCategory(Long categoryId, Pageable pageable) {
        return videoRepository.findByCategoryId(categoryId, pageable)
                .map(videoMapper::toDTO);
    }

    @Override
    public void deleteVideo(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));

        videoRepository.delete(video);
    }

    @Override
    public void incrementViewCount(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));

        video.setViewsCount(video.getViewsCount() + 1);
        videoRepository.save(video);
    }
}