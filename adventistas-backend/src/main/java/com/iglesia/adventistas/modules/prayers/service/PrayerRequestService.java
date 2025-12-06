package com.iglesia.adventistas.modules.prayers.service;

import com.iglesia.adventistas.modules.prayers.dto.CreatePrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.dto.PrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrayerRequestService {

    PrayerRequestDTO createRequest(CreatePrayerRequestDTO dto, Long userId);

    Page<PrayerRequestDTO> getAllRequests(Pageable pageable);

    Page<PrayerRequestDTO> getRequestsByStatus(RequestStatus status, Pageable pageable);

    PrayerRequestDTO getRequestById(Long id);

    List<PrayerRequestDTO> getMyRequests(Long userId);

    PrayerRequestDTO updateStatus(Long id, RequestStatus status, Long attendedBy);

    void deleteRequest(Long id);

    long countByStatus(RequestStatus status);
}
