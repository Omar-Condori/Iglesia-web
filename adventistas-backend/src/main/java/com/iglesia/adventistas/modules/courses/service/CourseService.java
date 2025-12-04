package com.iglesia.adventistas.modules.courses.service;

import com.iglesia.adventistas.modules.courses.dto.CourseDTO;
import com.iglesia.adventistas.modules.courses.dto.CreateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CourseDTO createCourse(CreateCourseRequest request);

    CourseDTO updateCourse(Long id, CreateCourseRequest request);

    CourseDTO getCourseById(Long id);

    CourseDTO getCourseBySlug(String slug);

    Page<CourseDTO> getAllCourses(Pageable pageable);

    Page<CourseDTO> getActiveCourses(Pageable pageable);

    Page<CourseDTO> getFeaturedCourses(Pageable pageable);

    Page<CourseDTO> getCoursesByCategory(Long categoryId, Pageable pageable);

    Page<CourseDTO> getCoursesByLevel(String level, Pageable pageable);

    void deleteCourse(Long id);

    void toggleFeatured(Long id);

    void incrementEnrollment(Long id);
}