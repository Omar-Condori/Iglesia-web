package com.iglesia.adventistas.modules.courses.service;

import com.iglesia.adventistas.modules.courses.dto.CourseDTO;
import com.iglesia.adventistas.modules.courses.dto.CreateCourseRequest;
import com.iglesia.adventistas.modules.courses.entity.Course;
import com.iglesia.adventistas.modules.courses.entity.CourseCategory;
import com.iglesia.adventistas.modules.courses.mapper.CourseMapper;
import com.iglesia.adventistas.modules.courses.repository.CourseCategoryRepository;
import com.iglesia.adventistas.modules.courses.repository.CourseRepository;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseDTO createCourse(CreateCourseRequest request) {
        log.info("Creating course: {}", request.getTitle());

        CourseCategory category = courseCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado"));

        Course course = courseMapper.toEntity(request);
        course.setCategory(category);
        course.setInstructor(instructor);

        Course saved = courseRepository.save(course);
        return courseMapper.toDTO(saved);
    }

    @Override
    public CourseDTO updateCourse(Long id, CreateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        courseMapper.updateEntity(request, course);

        if (!course.getCategory().getId().equals(request.getCategoryId())) {
            CourseCategory category = courseCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            course.setCategory(category);
        }

        if (!course.getInstructor().getId().equals(request.getInstructorId())) {
            User instructor = userRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado"));
            course.setInstructor(instructor);
        }

        Course updated = courseRepository.save(course);
        return courseMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        return courseMapper.toDTO(course);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseBySlug(String slug) {
        Course course = courseRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        return courseMapper.toDTO(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> getActiveCourses(Pageable pageable) {
        return courseRepository.findByIsActiveTrue(pageable)
                .map(courseMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> getFeaturedCourses(Pageable pageable) {
        return courseRepository.findFeaturedCourses(pageable)
                .map(courseMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> getCoursesByCategory(Long categoryId, Pageable pageable) {
        return courseRepository.findByCategoryId(categoryId, pageable)
                .map(courseMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> getCoursesByLevel(String level, Pageable pageable) {
        return courseRepository.findByLevel(level, pageable)
                .map(courseMapper::toDTO);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        courseRepository.delete(course);
    }

    @Override
    public void toggleFeatured(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        course.setIsFeatured(!course.getIsFeatured());
        courseRepository.save(course);
    }

    @Override
    public void incrementEnrollment(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        course.setEnrollmentsCount(course.getEnrollmentsCount() + 1);
        courseRepository.save(course);
    }
}