package com.iglesia.adventistas.modules.downloads.controller;

import com.iglesia.adventistas.modules.downloads.dto.CreateDownloadRequest;
import com.iglesia.adventistas.modules.downloads.dto.DownloadDTO;
import com.iglesia.adventistas.modules.downloads.service.DownloadService;
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
@RequestMapping("/api/downloads")
@RequiredArgsConstructor
@Tag(name = "Downloads", description = "Gestión de descargas")
public class DownloadController {

    private final DownloadService downloadService;

    @PostMapping
    @PreAuthorize("hasAuthority('downloads.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear descarga")
    public ResponseEntity<BaseResponse<DownloadDTO>> createDownload(
            @Valid @RequestBody CreateDownloadRequest request) {

        Long uploaderId = 1L; // TODO: Obtener del usuario autenticado
        DownloadDTO download = downloadService.createDownload(request, uploaderId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(download, "Descarga creada exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar descargas activas")
    public ResponseEntity<BaseResponse<Page<DownloadDTO>>> getActiveDownloads(Pageable pageable) {
        Page<DownloadDTO> downloads = downloadService.getActiveDownloads(pageable);
        return ResponseEntity.ok(BaseResponse.success(downloads));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener descarga por ID")
    public ResponseEntity<BaseResponse<DownloadDTO>> getDownloadById(@PathVariable Long id) {
        DownloadDTO download = downloadService.getDownloadById(id);
        return ResponseEntity.ok(BaseResponse.success(download));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener descarga por slug")
    public ResponseEntity<BaseResponse<DownloadDTO>> getDownloadBySlug(@PathVariable String slug) {
        DownloadDTO download = downloadService.getDownloadBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(download));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Listar descargas por categoría")
    public ResponseEntity<BaseResponse<Page<DownloadDTO>>> getDownloadsByCategory(
            @PathVariable Long categoryId, Pageable pageable) {

        Page<DownloadDTO> downloads = downloadService.getDownloadsByCategory(categoryId, pageable);
        return ResponseEntity.ok(BaseResponse.success(downloads));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('downloads.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar descarga")
    public ResponseEntity<BaseResponse<DownloadDTO>> updateDownload(
            @PathVariable Long id,
            @Valid @RequestBody CreateDownloadRequest request) {

        DownloadDTO download = downloadService.updateDownload(id, request);
        return ResponseEntity.ok(BaseResponse.success(download, "Descarga actualizada"));
    }

    @PostMapping("/{id}/increment")
    @Operation(summary = "Incrementar contador de descargas")
    public ResponseEntity<BaseResponse<Void>> incrementDownloadCount(@PathVariable Long id) {
        downloadService.incrementDownloadCount(id);
        return ResponseEntity.ok(BaseResponse.success(null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('downloads.delete')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar descarga")
    public ResponseEntity<BaseResponse<Void>> deleteDownload(@PathVariable Long id) {
        downloadService.deleteDownload(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Descarga eliminada"));
    }
}