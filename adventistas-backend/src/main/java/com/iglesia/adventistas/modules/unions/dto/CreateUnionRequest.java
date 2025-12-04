package com.iglesia.adventistas.modules.unions.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUnionRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    private String description;

    @NotBlank(message = "La dirección es requerida")
    private String address;

    @NotBlank(message = "La ciudad es requerida")
    private String city;

    @NotBlank(message = "El estado es requerido")
    private String state;

    @NotBlank(message = "El país es requerido")
    private String country;

    private String phone;

    @Email(message = "Email inválido")
    private String email;

    private String website;
    private String logoUrl;
}