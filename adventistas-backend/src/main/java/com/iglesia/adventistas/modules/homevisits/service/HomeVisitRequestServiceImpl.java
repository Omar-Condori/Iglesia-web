package com.iglesia.adventistas.modules.homevisits.service;

import com.iglesia.adventistas.modules.homevisits.dto.CreateHomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.dto.HomeVisitRequestDTO;
import com.iglesia.adventistas.modules.homevisits.entity.HomeVisitRequest;
import com.iglesia.adventistas.modules.homevisits.mapper.HomeVisitRequestMapper;
import com.iglesia.adventistas.modules.homevisits.repository.HomeVisitRequestRepository;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeVisitRequestServiceImpl implements HomeVisitRequestService {

    private final HomeVisitRequestRepository homeVisitRequestRepository;
    private final UserRepository userRepository;
    private final HomeVisitRequestMapper homeVisitRequestMapper;

    @Override
    @Transactional
    public HomeVisitRequestDTO createRequest(CreateHomeVisitRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        HomeVisitRequest request = homeVisitRequestMapper.toEntity(dto);
        request.setUser(user);

        HomeVisitRequest savedRequest = homeVisitRequestRepository.save(request);

        // TODO: Enviar email de notificación al admin si está configurado

        return homeVisitRequestMapper.toDTO(savedRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HomeVisitRequestDTO> getAllRequests(Pageable pageable) {
        return homeVisitRequestRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(homeVisitRequestMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HomeVisitRequestDTO> getRequestsByStatus(RequestStatus status, Pageable pageable) {
        return homeVisitRequestRepository.findByStatus(status, pageable)
                .map(homeVisitRequestMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public HomeVisitRequestDTO getRequestById(Long id) {
        HomeVisitRequest request = homeVisitRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));
        return homeVisitRequestMapper.toDTO(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeVisitRequestDTO> getMyRequests(Long userId) {
        return homeVisitRequestRepository.findByUserId(userId).stream()
                .map(homeVisitRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HomeVisitRequestDTO updateStatus(Long id, RequestStatus status, Long attendedBy) {
        HomeVisitRequest request = homeVisitRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));

        request.setStatus(status);

        if (attendedBy != null) {
            User attendee = userRepository.findById(attendedBy)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            request.setAttendedBy(attendee);
            request.setAttendedAt(LocalDateTime.now());
        }

        HomeVisitRequest updatedRequest = homeVisitRequestRepository.save(request);

        // TODO: Enviar email al usuario si wants_notifications = true

        return homeVisitRequestMapper.toDTO(updatedRequest);
    }

    @Override
    @Transactional
    public void deleteRequest(Long id) {
        HomeVisitRequest request = homeVisitRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));
        homeVisitRequestRepository.delete(request);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatus(RequestStatus status) {
        return homeVisitRequestRepository.countByStatus(status);
    }
}
