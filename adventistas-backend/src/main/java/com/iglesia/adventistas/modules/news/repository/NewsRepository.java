package com.iglesia.adventistas.modules.news.repository;

import com.iglesia.adventistas.modules.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.status = 'published' AND n.deletedAt IS NULL ORDER BY n.isFeatured DESC, n.publishedAt DESC")
    Page<News> findAllPublished(Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.slug = :slug AND n.deletedAt IS NULL")
    Optional<News> findBySlug(String slug);

    @Query("SELECT n FROM News n WHERE n.category.id = :categoryId AND n.deletedAt IS NULL")
    Page<News> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.category.id = :categoryId AND n.status = 'published' AND n.deletedAt IS NULL ORDER BY n.isFeatured DESC, n.publishedAt DESC")
    Page<News> findPublishedByCategory(Long categoryId, Pageable pageable);

    boolean existsBySlug(String slug);
}