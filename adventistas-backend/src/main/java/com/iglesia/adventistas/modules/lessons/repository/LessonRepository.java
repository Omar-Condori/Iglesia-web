package com.iglesia.adventistas.modules.lessons.repository;

import com.iglesia.adventistas.modules.lessons.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findBySlug(String slug);

    @Query("SELECT l FROM Lesson l WHERE l.course.id = :courseId AND l.isActive = true ORDER BY l.sortOrder")
    List<Lesson> findByCourseId(Long courseId);

    @Query("SELECT l FROM Lesson l WHERE l.course.id = :courseId AND l.isFree = true AND l.isActive = true ORDER BY l.sortOrder")
    List<Lesson> findFreeLessonsByCourseId(Long courseId);
}