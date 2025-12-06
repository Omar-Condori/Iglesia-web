package com.iglesia.adventistas.modules.churches.service;

import com.iglesia.adventistas.modules.churches.dto.ChurchDTO;
import com.iglesia.adventistas.modules.churches.dto.CreateChurchRequest;
import com.iglesia.adventistas.modules.churches.entity.Church;
import com.iglesia.adventistas.modules.churches.mapper.ChurchMapper;
import com.iglesia.adventistas.modules.churches.repository.ChurchRepository;
import com.iglesia.adventistas.modules.unions.entity.Union;
import com.iglesia.adventistas.modules.unions.repository.UnionRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChurchServiceImpl implements ChurchService {

    private final ChurchRepository churchRepository;
    private final UnionRepository unionRepository;
    private final ChurchMapper churchMapper;

    @Override
    public ChurchDTO createChurch(CreateChurchRequest request) {
        log.info("Creating church: {}", request.getName());

        Church church = churchMapper.toEntity(request);

        // Only set union if unionId is provided
        if (request.getUnionId() != null) {
            Union union = unionRepository.findById(request.getUnionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));
            church.setUnion(union);
        }

        Church saved = churchRepository.save(church);
        return churchMapper.toDTO(saved);
    }

    @Override
    public ChurchDTO updateChurch(Long id, CreateChurchRequest request) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada"));

        churchMapper.updateEntity(request, church);

        // Handle union assignment
        if (request.getUnionId() != null) {
            // Check if union needs to be updated
            if (church.getUnion() == null || !church.getUnion().getId().equals(request.getUnionId())) {
                Union union = unionRepository.findById(request.getUnionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));
                church.setUnion(union);
            }
        } else {
            // Remove union if unionId is null
            church.setUnion(null);
        }

        Church updated = churchRepository.save(church);
        return churchMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ChurchDTO getChurchById(Long id) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada"));

        return churchMapper.toDTO(church);
    }

    @Override
    @Transactional(readOnly = true)
    public ChurchDTO getChurchBySlug(String slug) {
        Church church = churchRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada"));

        return churchMapper.toDTO(church);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChurchDTO> getAllChurches(Pageable pageable) {
        return churchRepository.findAll(pageable)
                .map(churchMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChurchDTO> getActiveChurches(Pageable pageable) {
        return churchRepository.findByIsActiveTrue(pageable)
                .map(churchMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChurchDTO> getChurchesByUnion(Long unionId) {
        return churchRepository.findByUnionId(unionId).stream()
                .map(churchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChurchDTO> getChurchesByCity(String city) {
        return churchRepository.findByCity(city).stream()
                .map(churchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChurchDTO> getChurchesByState(String state) {
        return churchRepository.findByState(state).stream()
                .map(churchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChurch(Long id) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada"));

        churchRepository.delete(church);
    }

    @Override
    public void toggleActive(Long id) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada"));

        church.setIsActive(!church.getIsActive());
        churchRepository.save(church);
    }
}