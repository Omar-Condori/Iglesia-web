package com.iglesia.adventistas.modules.homevisits.repository;

import com.iglesia.adventistas.modules.homevisits.entity.HomeVisitRequest;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeVisitRequestRepository extends JpaRepository<HomeVisitRequest, Long> {

    Page<HomeVisitRequest> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<HomeVisitRequest> findByStatus(RequestStatus status, Pageable pageable);

    @Query("SELECT h FROM HomeVisitRequest h WHERE h.user.id = :userId ORDER BY h.createdAt DESC")
    List<HomeVisitRequest> findByUserId(@Param("userId") Long userId);

    long countByStatus(RequestStatus status);
}
