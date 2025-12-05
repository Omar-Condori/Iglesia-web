package com.iglesia.adventistas.modules.departments.controller;

import com.iglesia.adventistas.modules.departments.dto.CreateContentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentContentDTO;
import com.iglesia.adventistas.modules.departments.service.DepartmentContentService;
import com.iglesia.adventistas.shared.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department Content", description = "Gestión de contenido de departamentos")
public class DepartmentContentController {

    private final DepartmentContentService contentService;

    // Public endpoints
    @GetMapping("/sections/{sectionId}/content")
    @Operation(summary = "Obtener contenido publicado de una sección")
    public ResponseEntity<BaseResponse<List<DepartmentContentDTO>>> getPublishedContentBySection(
            @PathVariable Long sectionId) {
        List<DepartmentContentDTO> content = contentService.getContentBySection(sectionId, true);
        return ResponseEntity.ok(BaseResponse.success(content));
    }

    @GetMapping("/content/{id}")
    @Operation(summary = "Obtener contenido por ID")
    public ResponseEntity<BaseResponse<DepartmentContentDTO>> getContentById(@PathVariable Long id) {
        DepartmentContentDTO content = contentService.getContentById(id);
        return ResponseEntity.ok(BaseResponse.success(content));
    }

    // Admin endpoints
    @PostMapping("/sections/{sectionId}/content")
    @PreAuthorize("hasAnyAuthority('news.create', 'users.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear contenido en una sección")
    public ResponseEntity<BaseResponse<DepartmentContentDTO>> createContent(
            @PathVariable Long sectionId,
            @Valid @RequestBody CreateContentRequest request,
            Authentication authentication) {

        // Get user ID from authentication
        Long authorId = 1L; // TODO: Extract from authentication properly

        DepartmentContentDTO content = contentService.createContent(sectionId, request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(content, "Contenido creado exitosamente"));
    }

    @PutMapping("/content/{id}")
    @PreAuthorize("hasAnyAuthority('news.edit', 'users.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar contenido")
    public ResponseEntity<BaseResponse<DepartmentContentDTO>> updateContent(
            @PathVariable Long id,
            @Valid @RequestBody CreateContentRequest request) {

        DepartmentContentDTO content = contentService.updateContent(id, request);
        return ResponseEntity.ok(BaseResponse.success(content, "Contenido actualizado"));
    }

    @PatchMapping("/content/{id}/toggle-publish")
    @PreAuthorize("hasAnyAuthority('news.publish', 'users.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Publicar/Despublicar contenido")
    public ResponseEntity<BaseResponse<Void>> togglePublish(@PathVariable Long id) {
        contentService.togglePublish(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado de publicación actualizado"));
    }

    @DeleteMapping("/content/{id}")
    @PreAuthorize("hasAnyAuthority('news.create', 'users.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar contenido")
    public ResponseEntity<BaseResponse<Void>> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Contenido eliminado"));
    }

    @GetMapping("/admin/sections/{sectionId}/content")
    @PreAuthorize("hasAnyAuthority('news.view', 'users.view')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Obtener todo el contenido de una sección (admin)")
    public ResponseEntity<BaseResponse<List<DepartmentContentDTO>>> getAllContentBySection(
            @PathVariable Long sectionId) {
        List<DepartmentContentDTO> content = contentService.getContentBySection(sectionId, false);
        return ResponseEntity.ok(BaseResponse.success(content));
    }
}
