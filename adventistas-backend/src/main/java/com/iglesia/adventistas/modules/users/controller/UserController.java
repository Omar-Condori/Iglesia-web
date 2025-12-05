package com.iglesia.adventistas.modules.users.controller;

import com.iglesia.adventistas.modules.users.dto.AssignDepartmentRequest;
import com.iglesia.adventistas.modules.users.dto.CreateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UpdateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UserDepartmentDTO;
import com.iglesia.adventistas.modules.users.dto.UserResponse;
import com.iglesia.adventistas.modules.users.service.UserDepartmentService;
import com.iglesia.adventistas.modules.users.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Gestión de usuarios")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserService userService;
    private final UserDepartmentService userDepartmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('users.create')")
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema")
    public ResponseEntity<BaseResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse user = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(user, "Usuario creado exitosamente"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users.view')")
    @Operation(summary = "Obtener usuario por ID")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(BaseResponse.success(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users.view')")
    @Operation(summary = "Listar todos los usuarios")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> getAllUsers(Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(BaseResponse.success(users));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('users.edit')")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(BaseResponse.success(user, "Usuario actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users.delete')")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Usuario eliminado exitosamente"));
    }

    // ========== DEPARTMENT ASSIGNMENT ENDPOINTS ==========

    @GetMapping("/{id}/departments")
    @PreAuthorize("hasAuthority('users.view')")
    @Operation(summary = "Obtener departamentos asignados a un usuario")
    public ResponseEntity<BaseResponse<List<UserDepartmentDTO>>> getUserDepartments(@PathVariable Long id) {
        List<UserDepartmentDTO> departments = userDepartmentService.getUserDepartments(id);
        return ResponseEntity.ok(BaseResponse.success(departments));
    }

    @PostMapping("/{id}/departments")
    @PreAuthorize("hasAuthority('users.edit')")
    @Operation(summary = "Asignar departamento a usuario", description = "Asigna un departamento con permisos específicos")
    public ResponseEntity<BaseResponse<UserDepartmentDTO>> assignDepartment(
            @PathVariable Long id,
            @Valid @RequestBody AssignDepartmentRequest request) {

        UserDepartmentDTO result = userDepartmentService.assignDepartment(id, request);
        return ResponseEntity.ok(BaseResponse.success(result, "Departamento asignado exitosamente"));
    }

    @DeleteMapping("/{id}/departments/{departmentId}")
    @PreAuthorize("hasAuthority('users.edit')")
    @Operation(summary = "Remover departamento de usuario")
    public ResponseEntity<BaseResponse<Void>> removeDepartment(
            @PathVariable Long id,
            @PathVariable Long departmentId) {

        userDepartmentService.removeDepartment(id, departmentId);
        return ResponseEntity.ok(BaseResponse.success(null, "Departamento removido exitosamente"));
    }
}