package com.iglesia.adventistas.modules.courses.repository;

import com.iglesia.adventistas.modules.courses.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findBySlug(String slug);

    Page<Course> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.isFeatured = true AND c.isActive = true")
    Page<Course> findFeaturedCourses(Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.category.id = :categoryId AND c.isActive = true")
    Page<Course> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.level = :level AND c.isActive = true")
    Page<Course> findByLevel(String level, Pageable pageable);
}