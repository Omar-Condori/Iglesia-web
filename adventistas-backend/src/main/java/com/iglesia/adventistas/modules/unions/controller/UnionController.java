package com.iglesia.adventistas.modules.unions.controller;

import com.iglesia.adventistas.modules.unions.dto.CreateUnionRequest;
import com.iglesia.adventistas.modules.unions.dto.UnionDTO;
import com.iglesia.adventistas.modules.unions.service.UnionService;
import com.iglesia.adventistas.shared.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unions")
@RequiredArgsConstructor
@Tag(name = "Unions", description = "Gestión de uniones")
public class UnionController {

    private final UnionService unionService;

    @PostMapping
    @PreAuthorize("hasAuthority('unions.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear unión")
    public ResponseEntity<BaseResponse<UnionDTO>> createUnion(
            @Valid @RequestBody CreateUnionRequest request) {

        UnionDTO union = unionService.createUnion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(union, "Unión creada exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar todas las uniones")
    public ResponseEntity<BaseResponse<List<UnionDTO>>> getAllUnions() {
        List<UnionDTO> unions = unionService.getAllUnions();
        return ResponseEntity.ok(BaseResponse.success(unions));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar uniones activas")
    public ResponseEntity<BaseResponse<List<UnionDTO>>> getActiveUnions() {
        List<UnionDTO> unions = unionService.getActiveUnions();
        return ResponseEntity.ok(BaseResponse.success(unions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener unión por ID")
    public ResponseEntity<BaseResponse<UnionDTO>> getUnionById(@PathVariable Long id) {
        UnionDTO union = unionService.getUnionById(id);
        return ResponseEntity.ok(BaseResponse.success(union));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener unión por slug")
    public ResponseEntity<BaseResponse<UnionDTO>> getUnionBySlug(@PathVariable String slug) {
        UnionDTO union = unionService.getUnionBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(union));
    }

    @GetMapping("/country/{country}")
    @Operation(summary = "Listar uniones por país")
    public ResponseEntity<BaseResponse<List<UnionDTO>>> getUnionsByCountry(@PathVariable String country) {
        List<UnionDTO> unions = unionService.getUnionsByCountry(country);
        return ResponseEntity.ok(BaseResponse.success(unions));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('unions.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar unión")
    public ResponseEntity<BaseResponse<UnionDTO>> updateUnion(
            @PathVariable Long id,
            @Valid @RequestBody CreateUnionRequest request) {

        UnionDTO union = unionService.updateUnion(id, request);
        return ResponseEntity.ok(BaseResponse.success(union, "Unión actualizada"));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasAuthority('unions.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Activar/desactivar unión")
    public ResponseEntity<BaseResponse<Void>> toggleActive(@PathVariable Long id) {
        unionService.toggleActive(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado actualizado"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('unions.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar unión")
    public ResponseEntity<BaseResponse<Void>> deleteUnion(@PathVariable Long id) {
        unionService.deleteUnion(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Unión eliminada"));
    }
}