package com.iglesia.adventistas.modules.users.controller;

import com.iglesia.adventistas.modules.users.dto.CreateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UpdateUserRequest;
import com.iglesia.adventistas.modules.users.dto.UserResponse;
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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Gestión de usuarios")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserService userService;

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
}