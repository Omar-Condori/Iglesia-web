package com.iglesia.adventistas.modules.unions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnionDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String email;
    private String website;
    private String logoUrl;
    private Boolean isActive;
    private Integer churchesCount;
    private LocalDateTime createdAt;
}