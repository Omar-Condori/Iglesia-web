package com.iglesia.adventistas.modules.videos.repository;

import com.iglesia.adventistas.modules.videos.entity.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Long> {

    Optional<VideoCategory> findBySlug(String slug);

    List<VideoCategory> findByIsActiveTrueOrderBySortOrder();
}