package com.iglesia.adventistas.modules.lessons.service;

import com.iglesia.adventistas.modules.lessons.dto.CreateLessonRequest;
import com.iglesia.adventistas.modules.lessons.dto.LessonDTO;

import java.util.List;

public interface LessonService {

    LessonDTO createLesson(CreateLessonRequest request);

    LessonDTO updateLesson(Long id, CreateLessonRequest request);

    LessonDTO getLessonById(Long id);

    LessonDTO getLessonBySlug(String slug);

    List<LessonDTO> getLessonsByCourse(Long courseId);

    List<LessonDTO> getFreeLessonsByCourse(Long courseId);

    void deleteLesson(Long id);

    void toggleActive(Long id);
}