package com.iglesia.adventistas.modules.departments.repository;

import com.iglesia.adventistas.modules.departments.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findBySlug(String slug);

    List<Department> findByIsActiveTrueOrderBySortOrder();

    boolean existsBySlug(String slug);
}