package com.iglesia.adventistas.modules.downloads.repository;

import com.iglesia.adventistas.modules.downloads.entity.Download;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DownloadRepository extends JpaRepository<Download, Long> {

    Optional<Download> findBySlug(String slug);

    Page<Download> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT d FROM Download d WHERE d.category.id = :categoryId AND d.isActive = true")
    Page<Download> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT d FROM Download d WHERE d.department.id = :departmentId AND d.isActive = true")
    Page<Download> findByDepartmentId(Long departmentId, Pageable pageable);
}