package com.iglesia.adventistas.modules.about.controller;

import com.iglesia.adventistas.modules.about.dto.AboutSectionDTO;
import com.iglesia.adventistas.modules.about.dto.UpdateAboutSectionRequest;
import com.iglesia.adventistas.modules.about.service.AboutSectionService;
import com.iglesia.adventistas.shared.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
@Tag(name = "About Us", description = "Gestión de secciones Sobre Nosotros")
public class AboutSectionController {

    private final AboutSectionService aboutService;

    @GetMapping
    @Operation(summary = "Listar secciones activas de Sobre Nosotros")
    public ResponseEntity<BaseResponse<List<AboutSectionDTO>>> getActiveSections() {
        List<AboutSectionDTO> sections = aboutService.getAllActiveSections();
        return ResponseEntity.ok(BaseResponse.success(sections));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Obtener sección por slug")
    public ResponseEntity<BaseResponse<AboutSectionDTO>> getSectionBySlug(@PathVariable String slug) {
        AboutSectionDTO section = aboutService.getSectionBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(section));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('about.edit', 'users.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar sección (admin)")
    public ResponseEntity<BaseResponse<AboutSectionDTO>> updateSection(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAboutSectionRequest request) {
        AboutSectionDTO updated = aboutService.updateSection(id, request);
        return ResponseEntity.ok(BaseResponse.success(updated, "Sección actualizada exitosamente"));
    }
}
