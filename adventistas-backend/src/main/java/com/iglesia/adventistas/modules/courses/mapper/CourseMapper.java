package com.iglesia.adventistas.modules.courses.mapper;

import com.iglesia.adventistas.modules.courses.dto.CourseDTO;
import com.iglesia.adventistas.modules.courses.dto.CreateCourseRequest;
import com.iglesia.adventistas.modules.courses.entity.Course;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) return null;

        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .description(course.getDescription())
                .thumbnailUrl(course.getThumbnailUrl())
                .level(course.getLevel())
                .durationMinutes(course.getDurationMinutes())
                .isActive(course.getIsActive())
                .isFeatured(course.getIsFeatured())
                .enrollmentsCount(course.getEnrollmentsCount())
                .categoryName(course.getCategory() != null ? course.getCategory().getName() : null)
                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName() : null)
                .createdAt(course.getCreatedAt())
                .build();
    }

    public Course toEntity(CreateCourseRequest request) {
        if (request == null) return null;

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setSlug(SlugUtils.generateSlug(request.getTitle()));
        course.setDescription(request.getDescription());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setLevel(request.getLevel());
        course.setDurationMinutes(request.getDurationMinutes());
        course.setIsActive(true);
        course.setIsFeatured(false);
        course.setEnrollmentsCount(0);

        return course;
    }

    public void updateEntity(CreateCourseRequest request, Course course) {
        if (request == null || course == null) return;

        course.setTitle(request.getTitle());
        course.setSlug(SlugUtils.generateSlug(request.getTitle()));
        course.setDescription(request.getDescription());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setLevel(request.getLevel());
        course.setDurationMinutes(request.getDurationMinutes());
    }
}