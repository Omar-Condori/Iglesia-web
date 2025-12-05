package com.iglesia.adventistas.modules.users.entity;

import com.iglesia.adventistas.modules.departments.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "can_view")
    private Boolean canView = true;

    @Column(name = "can_edit")
    private Boolean canEdit = false;

    @Column(name = "can_publish")
    private Boolean canPublish = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
