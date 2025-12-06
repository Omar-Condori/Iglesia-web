package com.iglesia.adventistas.modules.prayers.repository;

import com.iglesia.adventistas.modules.prayers.entity.PrayerRequest;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrayerRequestRepository extends JpaRepository<PrayerRequest, Long> {

    Page<PrayerRequest> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<PrayerRequest> findByStatus(RequestStatus status, Pageable pageable);

    @Query("SELECT pr FROM PrayerRequest pr WHERE pr.user.id = :userId ORDER BY pr.createdAt DESC")
    List<PrayerRequest> findByUserId(@Param("userId") Long userId);

    long countByStatus(RequestStatus status);
}
