package com.iglesia.adventistas.modules.categories.controller;

import com.iglesia.adventistas.modules.categories.dto.CategoryDTO;
import com.iglesia.adventistas.modules.categories.dto.CreateCategoryRequest;
import com.iglesia.adventistas.modules.categories.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Gestión de categorías")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority('categories.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear categoría")
    public ResponseEntity<BaseResponse<CategoryDTO>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request) {

        CategoryDTO category = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(category, "Categoría creada exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar todas las categorías")
    public ResponseEntity<BaseResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(BaseResponse.success(categories));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar categorías activas")
    public ResponseEntity<BaseResponse<List<CategoryDTO>>> getActiveCategories() {
        List<CategoryDTO> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(BaseResponse.success(categories));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public ResponseEntity<BaseResponse<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(BaseResponse.success(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('categories.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar categoría")
    public ResponseEntity<BaseResponse<CategoryDTO>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryRequest request) {

        CategoryDTO category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(BaseResponse.success(category, "Categoría actualizada"));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasAuthority('categories.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Activar/desactivar categoría")
    public ResponseEntity<BaseResponse<Void>> toggleActive(@PathVariable Long id) {
        categoryService.toggleActive(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado actualizado"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('categories.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar categoría")
    public ResponseEntity<BaseResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Categoría eliminada"));
    }
}