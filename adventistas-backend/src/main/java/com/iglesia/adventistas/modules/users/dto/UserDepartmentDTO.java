package com.iglesia.adventistas.modules.users.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDepartmentDTO {
    private Long id;
    private Long userId;
    private Long departmentId;
    private String departmentName;
    private String departmentSlug;
    private Boolean canView;
    private Boolean canEdit;
    private Boolean canPublish;
}
