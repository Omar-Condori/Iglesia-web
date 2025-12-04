package com.iglesia.adventistas.modules.lessons.mapper;

import com.iglesia.adventistas.modules.lessons.dto.CreateLessonRequest;
import com.iglesia.adventistas.modules.lessons.dto.LessonDTO;
import com.iglesia.adventistas.modules.lessons.entity.Lesson;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public LessonDTO toDTO(Lesson lesson) {
        if (lesson == null) return null;

        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .slug(lesson.getSlug())
                .description(lesson.getDescription())
                .content(lesson.getContent())
                .videoUrl(lesson.getVideoUrl())
                .durationMinutes(lesson.getDurationMinutes())
                .sortOrder(lesson.getSortOrder())
                .isActive(lesson.getIsActive())
                .isFree(lesson.getIsFree())
                .courseTitle(lesson.getCourse() != null ? lesson.getCourse().getTitle() : null)
                .createdAt(lesson.getCreatedAt())
                .build();
    }

    public Lesson toEntity(CreateLessonRequest request) {
        if (request == null) return null;

        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setSlug(SlugUtils.generateSlug(request.getTitle()));
        lesson.setDescription(request.getDescription());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setDurationMinutes(request.getDurationMinutes());
        lesson.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        lesson.setIsActive(true);
        lesson.setIsFree(request.getIsFree() != null ? request.getIsFree() : false);

        return lesson;
    }

    public void updateEntity(CreateLessonRequest request, Lesson lesson) {
        if (request == null || lesson == null) return;

        lesson.setTitle(request.getTitle());
        lesson.setSlug(SlugUtils.generateSlug(request.getTitle()));
        lesson.setDescription(request.getDescription());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setDurationMinutes(request.getDurationMinutes());
        if (request.getSortOrder() != null) {
            lesson.setSortOrder(request.getSortOrder());
        }
        if (request.getIsFree() != null) {
            lesson.setIsFree(request.getIsFree());
        }
    }
}