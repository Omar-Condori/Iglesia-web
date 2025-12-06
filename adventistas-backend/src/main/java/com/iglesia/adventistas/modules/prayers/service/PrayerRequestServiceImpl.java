package com.iglesia.adventistas.modules.prayers.service;

import com.iglesia.adventistas.modules.prayers.dto.CreatePrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.dto.PrayerRequestDTO;
import com.iglesia.adventistas.modules.prayers.entity.PrayerRequest;
import com.iglesia.adventistas.modules.prayers.entity.RequestStatus;
import com.iglesia.adventistas.modules.prayers.mapper.PrayerRequestMapper;
import com.iglesia.adventistas.modules.prayers.repository.PrayerRequestRepository;
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
public class PrayerRequestServiceImpl implements PrayerRequestService {

    private final PrayerRequestRepository prayerRequestRepository;
    private final UserRepository userRepository;
    private final PrayerRequestMapper prayerRequestMapper;

    @Override
    @Transactional
    public PrayerRequestDTO createRequest(CreatePrayerRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        PrayerRequest request = prayerRequestMapper.toEntity(dto);
        request.setUser(user);

        PrayerRequest savedRequest = prayerRequestRepository.save(request);

        // TODO: Enviar email de notificación al admin si está configurado

        return prayerRequestMapper.toDTO(savedRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrayerRequestDTO> getAllRequests(Pageable pageable) {
        return prayerRequestRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(prayerRequestMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrayerRequestDTO> getRequestsByStatus(RequestStatus status, Pageable pageable) {
        return prayerRequestRepository.findByStatus(status, pageable)
                .map(prayerRequestMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PrayerRequestDTO getRequestById(Long id) {
        PrayerRequest request = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada"));
        return prayerRequestMapper.toDTO(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrayerRequestDTO> getMyRequests(Long userId) {
        return prayerRequestRepository.findByUserId(userId).stream()
                .map(prayerRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PrayerRequestDTO updateStatus(Long id, RequestStatus status, Long attendedBy) {
        PrayerRequest request = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada"));

        request.setStatus(status);

        if (attendedBy != null) {
            User attendee = userRepository.findById(attendedBy)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            request.setAttendedBy(attendee);
            request.setAttendedAt(LocalDateTime.now());
        }

        PrayerRequest updatedRequest = prayerRequestRepository.save(request);

        // TODO: Enviar email al usuario si wants_notifications = true

        return prayerRequestMapper.toDTO(updatedRequest);
    }

    @Override
    @Transactional
    public void deleteRequest(Long id) {
        PrayerRequest request = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Petición no encontrada"));
        prayerRequestRepository.delete(request);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatus(RequestStatus status) {
        return prayerRequestRepository.countByStatus(status);
    }
}
