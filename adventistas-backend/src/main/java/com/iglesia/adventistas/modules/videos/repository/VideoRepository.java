package com.iglesia.adventistas.modules.videos.repository;

import com.iglesia.adventistas.modules.videos.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findBySlug(String slug);

    Page<Video> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT v FROM Video v WHERE v.category.id = :categoryId AND v.isActive = true")
    Page<Video> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT v FROM Video v WHERE v.platform = :platform AND v.isActive = true")
    Page<Video> findByPlatform(String platform, Pageable pageable);
}