package com.iglesia.adventistas.modules.lessons.controller;

import com.iglesia.adventistas.modules.lessons.dto.CreateLessonRequest;
import com.iglesia.adventistas.modules.lessons.dto.LessonDTO;
import com.iglesia.adventistas.modules.lessons.service.LessonService;
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
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Tag(name = "Lessons", description = "Gestión de lecciones")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @PreAuthorize("hasAuthority('lessons.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear lección")
    public ResponseEntity<BaseResponse<LessonDTO>> createLesson(
            @Valid @RequestBody CreateLessonRequest request) {

        LessonDTO lesson = lessonService.createLesson(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(lesson, "Lección creada exitosamente"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener lección por ID")
    public ResponseEntity<BaseResponse<LessonDTO>> getLessonById(@PathVariable Long id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(BaseResponse.success(lesson));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener lección por slug")
    public ResponseEntity<BaseResponse<LessonDTO>> getLessonBySlug(@PathVariable String slug) {
        LessonDTO lesson = lessonService.getLessonBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(lesson));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Listar lecciones por curso")
    public ResponseEntity<BaseResponse<List<LessonDTO>>> getLessonsByCourse(@PathVariable Long courseId) {
        List<LessonDTO> lessons = lessonService.getLessonsByCourse(courseId);
        return ResponseEntity.ok(BaseResponse.success(lessons));
    }

    @GetMapping("/course/{courseId}/free")
    @Operation(summary = "Listar lecciones gratuitas por curso")
    public ResponseEntity<BaseResponse<List<LessonDTO>>> getFreeLessonsByCourse(@PathVariable Long courseId) {
        List<LessonDTO> lessons = lessonService.getFreeLessonsByCourse(courseId);
        return ResponseEntity.ok(BaseResponse.success(lessons));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('lessons.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar lección")
    public ResponseEntity<BaseResponse<LessonDTO>> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody CreateLessonRequest request) {

        LessonDTO lesson = lessonService.updateLesson(id, request);
        return ResponseEntity.ok(BaseResponse.success(lesson, "Lección actualizada"));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasAuthority('lessons.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Activar/desactivar lección")
    public ResponseEntity<BaseResponse<Void>> toggleActive(@PathVariable Long id) {
        lessonService.toggleActive(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado actualizado"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lessons.delete')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar lección")
    public ResponseEntity<BaseResponse<Void>> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Lección eliminada"));
    }
}