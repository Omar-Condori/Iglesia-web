package com.iglesia.adventistas.security;

import com.iglesia.adventistas.shared.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Autenticación y autorización")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica usuario y retorna JWT token")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        JwtResponse response = new JwtResponse(jwt, "Bearer");

        return ResponseEntity.ok(BaseResponse.success(response, "Login exitoso"));
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
}