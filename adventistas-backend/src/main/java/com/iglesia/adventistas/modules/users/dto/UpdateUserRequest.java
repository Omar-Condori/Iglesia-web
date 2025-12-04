package com.iglesia.adventistas.modules.users.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Email(message = "Email inválido")
    private String email;

    private String firstName;
    private String lastName;
    private String phone;
    private String avatarUrl;
    private Boolean isActive;
    private Set<Long> roleIds;
}