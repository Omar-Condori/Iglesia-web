package com.iglesia.adventistas.modules.churches.repository;

import com.iglesia.adventistas.modules.churches.entity.Church;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChurchRepository extends JpaRepository<Church, Long> {

    Optional<Church> findBySlug(String slug);

    Page<Church> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT c FROM Church c WHERE c.union.id = :unionId AND c.isActive = true")
    List<Church> findByUnionId(Long unionId);

    @Query("SELECT c FROM Church c WHERE c.city = :city AND c.isActive = true")
    List<Church> findByCity(String city);

    @Query("SELECT c FROM Church c WHERE c.state = :state AND c.isActive = true")
    List<Church> findByState(String state);
}