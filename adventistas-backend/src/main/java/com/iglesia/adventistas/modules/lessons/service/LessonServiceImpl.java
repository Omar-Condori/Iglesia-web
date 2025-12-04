package com.iglesia.adventistas.modules.lessons.service;

import com.iglesia.adventistas.modules.courses.entity.Course;
import com.iglesia.adventistas.modules.courses.repository.CourseRepository;
import com.iglesia.adventistas.modules.lessons.dto.CreateLessonRequest;
import com.iglesia.adventistas.modules.lessons.dto.LessonDTO;
import com.iglesia.adventistas.modules.lessons.entity.Lesson;
import com.iglesia.adventistas.modules.lessons.mapper.LessonMapper;
import com.iglesia.adventistas.modules.lessons.repository.LessonRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    @Override
    public LessonDTO createLesson(CreateLessonRequest request) {
        log.info("Creating lesson: {}", request.getTitle());

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setCourse(course);

        Lesson saved = lessonRepository.save(lesson);
        return lessonMapper.toDTO(saved);
    }

    @Override
    public LessonDTO updateLesson(Long id, CreateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lección no encontrada"));

        lessonMapper.updateEntity(request, lesson);

        if (!lesson.getCourse().getId().equals(request.getCourseId())) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
            lesson.setCourse(course);
        }

        Lesson updated = lessonRepository.save(lesson);
        return lessonMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lección no encontrada"));

        return lessonMapper.toDTO(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonDTO getLessonBySlug(String slug) {
        Lesson lesson = lessonRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Lección no encontrada"));

        return lessonMapper.toDTO(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseId(courseId).stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getFreeLessonsByCourse(Long courseId) {
        return lessonRepository.findFreeLessonsByCourseId(courseId).stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lección no encontrada"));

        lessonRepository.delete(lesson);
    }

    @Override
    public void toggleActive(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lección no encontrada"));

        lesson.setIsActive(!lesson.getIsActive());
        lessonRepository.save(lesson);
    }
}