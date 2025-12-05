package com.iglesia.adventistas.modules.users.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignDepartmentRequest {
    private Long departmentId;
    private Boolean canView;
    private Boolean canEdit;
    private Boolean canPublish;
}
