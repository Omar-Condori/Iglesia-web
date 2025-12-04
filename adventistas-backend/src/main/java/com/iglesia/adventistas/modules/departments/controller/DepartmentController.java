package com.iglesia.adventistas.modules.departments.controller;

import com.iglesia.adventistas.modules.departments.dto.CreateDepartmentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentDTO;
import com.iglesia.adventistas.modules.departments.service.DepartmentService;
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
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Departments", description = "Gestión de departamentos")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('departments.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear departamento")
    public ResponseEntity<BaseResponse<DepartmentDTO>> createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request) {

        DepartmentDTO department = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(department, "Departamento creado exitosamente"));
    }

    @GetMapping
    @Operation(summary = "Listar todos los departamentos")
    public ResponseEntity<BaseResponse<List<DepartmentDTO>>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(BaseResponse.success(departments));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar departamentos activos")
    public ResponseEntity<BaseResponse<List<DepartmentDTO>>> getActiveDepartments() {
        List<DepartmentDTO> departments = departmentService.getActiveDepartments();
        return ResponseEntity.ok(BaseResponse.success(departments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener departamento por ID")
    public ResponseEntity<BaseResponse<DepartmentDTO>> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(BaseResponse.success(department));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('departments.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar departamento")
    public ResponseEntity<BaseResponse<DepartmentDTO>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody CreateDepartmentRequest request) {

        DepartmentDTO department = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(BaseResponse.success(department, "Departamento actualizado"));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasAuthority('departments.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Activar/desactivar departamento")
    public ResponseEntity<BaseResponse<Void>> toggleActive(@PathVariable Long id) {
        departmentService.toggleActive(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Estado actualizado"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('departments.manage')")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar departamento")
    public ResponseEntity<BaseResponse<Void>> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Departamento eliminado"));
    }
}