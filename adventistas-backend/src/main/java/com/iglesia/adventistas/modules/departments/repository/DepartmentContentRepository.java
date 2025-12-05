package com.iglesia.adventistas.modules.departments.repository;

import com.iglesia.adventistas.modules.departments.entity.DepartmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentContentRepository extends JpaRepository<DepartmentContent, Long> {

    List<DepartmentContent> findBySectionIdAndIsPublishedTrueOrderBySortOrder(Long sectionId);

    List<DepartmentContent> findBySectionIdOrderBySortOrder(Long sectionId);

    Optional<DepartmentContent> findBySectionIdAndSlug(Long sectionId, String slug);

    List<DepartmentContent> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
}
