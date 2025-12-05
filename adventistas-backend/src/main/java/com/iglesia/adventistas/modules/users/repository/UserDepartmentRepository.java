package com.iglesia.adventistas.modules.users.repository;

import com.iglesia.adventistas.modules.users.entity.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {

    List<UserDepartment> findByUserId(Long userId);

    Optional<UserDepartment> findByUserIdAndDepartmentId(Long userId, Long departmentId);

    void deleteByUserIdAndDepartmentId(Long userId, Long departmentId);

    boolean existsByUserIdAndDepartmentId(Long userId, Long departmentId);
}
