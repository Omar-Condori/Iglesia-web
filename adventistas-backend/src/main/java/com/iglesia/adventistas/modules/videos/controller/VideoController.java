package com.iglesia.adventistas.modules.videos.controller;

import com.iglesia.adventistas.modules.videos.dto.CreateVideoRequest;
import com.iglesia.adventistas.modules.videos.dto.VideoDTO;
import com.iglesia.adventistas.modules.videos.service.VideoService;
import com.iglesia.adventistas.shared.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@Tag(name = "Videos", description = "Gestión de videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @PreAuthorize("hasAuthority('videos.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear video")
    public ResponseEntity<BaseResponse<VideoDTO>> createVideo(
            @Valid @RequestBody CreateVideoRequest request) {

        Long uploaderId = 1L; // TODO: Obtener del usuario autenticado
        VideoDTO video = videoService.createVideo(request, uploaderId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(video, "Video creado exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar videos activos")
    public ResponseEntity<BaseResponse<Page<VideoDTO>>> getActiveVideos(Pageable pageable) {
        Page<VideoDTO> videos = videoService.getActiveVideos(pageable);
        return ResponseEntity.ok(BaseResponse.success(videos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener video por ID")
    public ResponseEntity<BaseResponse<VideoDTO>> getVideoById(@PathVariable Long id) {
        VideoDTO video = videoService.getVideoById(id);
        return ResponseEntity.ok(BaseResponse.success(video));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener video por slug")
    public ResponseEntity<BaseResponse<VideoDTO>> getVideoBySlug(@PathVariable String slug) {
        VideoDTO video = videoService.getVideoBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(video));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Listar videos por categoría")
    public ResponseEntity<BaseResponse<Page<VideoDTO>>> getVideosByCategory(
            @PathVariable Long categoryId, Pageable pageable) {

        Page<VideoDTO> videos = videoService.getVideosByCategory(categoryId, pageable);
        return ResponseEntity.ok(BaseResponse.success(videos));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('videos.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar video")
    public ResponseEntity<BaseResponse<VideoDTO>> updateVideo(
            @PathVariable Long id,
            @Valid @RequestBody CreateVideoRequest request) {

        VideoDTO video = videoService.updateVideo(id, request);
        return ResponseEntity.ok(BaseResponse.success(video, "Video actualizado"));
    }

    @PostMapping("/{id}/view")
    @Operation(summary = "Incrementar contador de vistas")
    public ResponseEntity<BaseResponse<Void>> incrementViewCount(@PathVariable Long id) {
        videoService.incrementViewCount(id);
        return ResponseEntity.ok(BaseResponse.success(null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('videos.delete')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar video")
    public ResponseEntity<BaseResponse<Void>> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Video eliminado"));
    }
}