package com.iglesia.adventistas.modules.unions.repository;

import com.iglesia.adventistas.modules.unions.entity.Union;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnionRepository extends JpaRepository<Union, Long> {

    Optional<Union> findBySlug(String slug);

    List<Union> findByIsActiveTrueOrderByName();

    List<Union> findByCountry(String country);
}