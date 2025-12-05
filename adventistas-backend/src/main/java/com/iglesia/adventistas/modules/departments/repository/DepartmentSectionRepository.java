package com.iglesia.adventistas.modules.departments.repository;

import com.iglesia.adventistas.modules.departments.entity.DepartmentSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentSectionRepository extends JpaRepository<DepartmentSection, Long> {

    List<DepartmentSection> findByDepartmentIdAndIsActiveTrue(Long departmentId);

    Optional<DepartmentSection> findByDepartmentIdAndSlug(Long departmentId, String slug);

    List<DepartmentSection> findByDepartmentIdOrderBySortOrder(Long departmentId);
}
