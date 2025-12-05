package com.iglesia.adventistas.modules.about.repository;

import com.iglesia.adventistas.modules.about.entity.AboutSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AboutSectionRepository extends JpaRepository<AboutSection, Long> {

    Optional<AboutSection> findBySlug(String slug);

    List<AboutSection> findAllByIsActiveTrueOrderBySortOrder();

    boolean existsBySlug(String slug);
}
