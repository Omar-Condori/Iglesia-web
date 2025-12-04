package com.iglesia.adventistas.modules.downloads.repository;

import com.iglesia.adventistas.modules.downloads.entity.DownloadCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DownloadCategoryRepository extends JpaRepository<DownloadCategory, Long> {

    Optional<DownloadCategory> findBySlug(String slug);

    List<DownloadCategory> findByIsActiveTrueOrderBySortOrder();
}