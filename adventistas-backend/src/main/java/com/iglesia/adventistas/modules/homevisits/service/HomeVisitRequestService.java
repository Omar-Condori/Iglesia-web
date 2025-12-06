package com.iglesia.adventistas.modules.homevisits.service;

import com.iglesia.adventistas.modules.homevisits.dto.CreateHomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.dto.HomeVisitRequestDTO;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeVisitRequestService {

    HomeVisitRequestDTO createRequest(CreateHomeVisitRequestDTO dto, Long userId);

    Page<HomeVisitRequestDTO> getAllRequests(Pageable pageable);

    Page<HomeVisitRequestDTO> getRequestsByStatus(RequestStatus status, Pageable pageable);

    HomeVisitRequestDTO getRequestById(Long id);

    List<HomeVisitRequestDTO> getMyRequests(Long userId);

    HomeVisitRequestDTO updateStatus(Long id, RequestStatus status, Long attendedBy);

    void deleteRequest(Long id);

    long countByStatus(RequestStatus status);
}
