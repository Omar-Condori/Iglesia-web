package com.iglesia.adventistas.security;

import com.iglesia.adventistas.modules.roles.entity.Role;
import com.iglesia.adventistas.modules.roles.repository.RoleRepository;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.base.BaseResponse;
import com.iglesia.adventistas.shared.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Autenticación y autorización")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica usuario y retorna JWT token")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        JwtResponse response = new JwtResponse(jwt, "Bearer");

        return ResponseEntity.ok(BaseResponse.success(response, "Login exitoso"));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario")
    public ResponseEntity<BaseResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        // Validar que el email no esté registrado
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getNombres());
        user.setLastName(request.getApellidoPaterno() + " " + request.getApellidoMaterno());
        user.setIsActive(true);

        // Asignar rol de usuario por defecto
        Role userRole = roleRepository.findBySlug("user")
                .orElseThrow(() -> new BusinessException("Rol de usuario no encontrado. Contacte al administrador."));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Guardar usuario
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(null, "Usuario registrado exitosamente"));
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        private String accessToken;
        private String tokenType;
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "El email es requerido")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "La contraseña es requerida")
        private String password;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Los nombres son requeridos")
        private String nombres;

        @NotBlank(message = "El apellido paterno es requerido")
        private String apellidoPaterno;

        @NotBlank(message = "El apellido materno es requerido")
        private String apellidoMaterno;

        @NotBlank(message = "El email es requerido")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        private String password;

        private Boolean miembroIglesia;
    }
}