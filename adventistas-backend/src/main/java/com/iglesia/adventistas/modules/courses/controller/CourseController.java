package com.iglesia.adventistas.modules.courses.controller;

import com.iglesia.adventistas.modules.courses.dto.CourseDTO;
import com.iglesia.adventistas.modules.courses.dto.CreateCourseRequest;
import com.iglesia.adventistas.modules.courses.service.CourseService;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Gestión de cursos")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAuthority('courses.create')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear curso")
    public ResponseEntity<BaseResponse<CourseDTO>> createCourse(
            @Valid @RequestBody CreateCourseRequest request) {

        CourseDTO course = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(course, "Curso creado exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar cursos activos")
    public ResponseEntity<BaseResponse<Page<CourseDTO>>> getActiveCourses(Pageable pageable) {
        Page<CourseDTO> courses = courseService.getActiveCourses(pageable);
        return ResponseEntity.ok(BaseResponse.success(courses));
    }

    @GetMapping("/featured")
    @Operation(summary = "Listar cursos destacados")
    public ResponseEntity<BaseResponse<Page<CourseDTO>>> getFeaturedCourses(Pageable pageable) {
        Page<CourseDTO> courses = courseService.getFeaturedCourses(pageable);
        return ResponseEntity.ok(BaseResponse.success(courses));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso por ID")
    public ResponseEntity<BaseResponse<CourseDTO>> getCourseById(@PathVariable Long id) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(BaseResponse.success(course));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Obtener curso por slug")
    public ResponseEntity<BaseResponse<CourseDTO>> getCourseBySlug(@PathVariable String slug) {
        CourseDTO course = courseService.getCourseBySlug(slug);
        return ResponseEntity.ok(BaseResponse.success(course));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Listar cursos por categoría")
    public ResponseEntity<BaseResponse<Page<CourseDTO>>> getCoursesByCategory(
            @PathVariable Long categoryId, Pageable pageable) {

        Page<CourseDTO> courses = courseService.getCoursesByCategory(categoryId, pageable);
        return ResponseEntity.ok(BaseResponse.success(courses));
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Listar cursos por nivel")
    public ResponseEntity<BaseResponse<Page<CourseDTO>>> getCoursesByLevel(
            @PathVariable String level, Pageable pageable) {

        Page<CourseDTO> courses = courseService.getCoursesByLevel(level, pageable);
        return ResponseEntity.ok(BaseResponse.success(courses));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('courses.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar curso")
    public ResponseEntity<BaseResponse<CourseDTO>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CreateCourseRequest request) {

        CourseDTO course = courseService.updateCourse(id, request);
        return ResponseEntity.ok(BaseResponse.success(course, "Curso actualizado"));
    }

    @PatchMapping("/{id}/featured")
    @PreAuthorize("hasAuthority('courses.edit')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Destacar/quitar curso")
    public ResponseEntity<BaseResponse<Void>> toggleFeatured(@PathVariable Long id) {
        courseService.toggleFeatured(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado actualizado"));
    }

    @PostMapping("/{id}/enroll")
    @Operation(summary = "Inscribirse en curso")
    public ResponseEntity<BaseResponse<Void>> incrementEnrollment(@PathVariable Long id) {
        courseService.incrementEnrollment(id);
        return ResponseEntity.ok(BaseResponse.success(null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('courses.delete')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar curso")
    public ResponseEntity<BaseResponse<Void>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Curso eliminado"));
    }
}