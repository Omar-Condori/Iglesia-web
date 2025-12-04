package com.iglesia.adventistas.modules.courses.repository;

import com.iglesia.adventistas.modules.courses.entity.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {

    Optional<CourseCategory> findBySlug(String slug);

    List<CourseCategory> findByIsActiveTrueOrderBySortOrder();
}