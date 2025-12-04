package com.iglesia.adventistas.modules.churches.controller;

import com.iglesia.adventistas.modules.churches.dto.ChurchDTO;
import com.iglesia.adventistas.modules.churches.dto.CreateChurchRequest;
import com.iglesia.adventistas.modules.churches.service.ChurchService;
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

import java.util.List;

@RestController
@RequestMapping("/api/churches")
@RequiredArgsConstructor
@Tag(name = "Churches", description = "Gestión de iglesias")
public class ChurchController {

    private final ChurchService churchService;

    @PostMapping
    @PreAuthorize("hasAuthority('churches.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear iglesia")
    public ResponseEntity<BaseResponse<ChurchDTO>> createChurch(
            @Valid @RequestBody CreateChurchRequest request) {

        ChurchDTO church = churchService.createChurch(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(church, "Iglesia creada exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar iglesias activas")
    public ResponseEntity<BaseResponse<Page<ChurchDTO>>> getActiveChurches(Pageable pageable) {
        Page<ChurchDTO> churches = churchService.getActiveChurches(pageable);
        return ResponseEntity.ok(BaseResponse.success(churches));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener iglesia por ID")
    public ResponseEntity<BaseResponse<ChurchDTO>> getChurchById(@PathVariable Long id) {
        ChurchDTO church = churchService.getChurchById(id);
        return ResponseEntity.ok(BaseResponse.success(church));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener iglesia por slug")
    public ResponseEntity<BaseResponse<ChurchDTO>> getChurchBySlug(@PathVariable String slug) {
        ChurchDTO church = churchService.getChurchBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(church));
    }

    @GetMapping("/union/{unionId}")
    @Operation(summary = "Listar iglesias por unión")
    public ResponseEntity<BaseResponse<List<ChurchDTO>>> getChurchesByUnion(@PathVariable Long unionId) {
        List<ChurchDTO> churches = churchService.getChurchesByUnion(unionId);
        return ResponseEntity.ok(BaseResponse.success(churches));
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Listar iglesias por ciudad")
    public ResponseEntity<BaseResponse<List<ChurchDTO>>> getChurchesByCity(@PathVariable String city) {
        List<ChurchDTO> churches = churchService.getChurchesByCity(city);
        return ResponseEntity.ok(BaseResponse.success(churches));
    }

    @GetMapping("/state/{state}")
    @Operation(summary = "Listar iglesias por estado")
    public ResponseEntity<BaseResponse<List<ChurchDTO>>> getChurchesByState(@PathVariable String state) {
        List<ChurchDTO> churches = churchService.getChurchesByState(state);
        return ResponseEntity.ok(BaseResponse.success(churches));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('churches.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar iglesia")
    public ResponseEntity<BaseResponse<ChurchDTO>> updateChurch(
            @PathVariable Long id,
            @Valid @RequestBody CreateChurchRequest request) {

        ChurchDTO church = churchService.updateChurch(id, request);
        return ResponseEntity.ok(BaseResponse.success(church, "Iglesia actualizada"));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasAuthority('churches.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Activar/desactivar iglesia")
    public ResponseEntity<BaseResponse<Void>> toggleActive(@PathVariable Long id) {
        churchService.toggleActive(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado actualizado"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('churches.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar iglesia")
    public ResponseEntity<BaseResponse<Void>> deleteChurch(@PathVariable Long id) {
        churchService.deleteChurch(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Iglesia eliminada"));
    }
}