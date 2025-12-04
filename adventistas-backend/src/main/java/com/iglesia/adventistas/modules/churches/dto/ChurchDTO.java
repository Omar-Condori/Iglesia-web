package com.iglesia.adventistas.modules.churches.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChurchDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String email;
    private String website;
    private Double latitude;
    private Double longitude;
    private Boolean isActive;
    private String unionName;
    private LocalDateTime createdAt;
}