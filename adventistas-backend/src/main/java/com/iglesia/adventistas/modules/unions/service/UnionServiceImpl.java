package com.iglesia.adventistas.modules.unions.service;

import com.iglesia.adventistas.modules.unions.dto.CreateUnionRequest;
import com.iglesia.adventistas.modules.unions.dto.UnionDTO;
import com.iglesia.adventistas.modules.unions.entity.Union;
import com.iglesia.adventistas.modules.unions.mapper.UnionMapper;
import com.iglesia.adventistas.modules.unions.repository.UnionRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UnionServiceImpl implements UnionService {

    private final UnionRepository unionRepository;
    private final UnionMapper unionMapper;

    @Override
    public UnionDTO createUnion(CreateUnionRequest request) {
        log.info("Creating union: {}", request.getName());

        Union union = unionMapper.toEntity(request);
        Union saved = unionRepository.save(union);

        return unionMapper.toDTO(saved);
    }

    @Override
    public UnionDTO updateUnion(Long id, CreateUnionRequest request) {
        Union union = unionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));

        unionMapper.updateEntity(request, union);
        Union updated = unionRepository.save(union);

        return unionMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public UnionDTO getUnionById(Long id) {
        Union union = unionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));

        return unionMapper.toDTO(union);
    }

    @Override
    @Transactional(readOnly = true)
    public UnionDTO getUnionBySlug(String slug) {
        Union union = unionRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));

        return unionMapper.toDTO(union);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnionDTO> getAllUnions() {
        return unionRepository.findAll().stream()
                .map(unionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnionDTO> getActiveUnions() {
        return unionRepository.findByIsActiveTrueOrderByName().stream()
                .map(unionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnionDTO> getUnionsByCountry(String country) {
        return unionRepository.findByCountry(country).stream()
                .map(unionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUnion(Long id) {
        Union union = unionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));

        unionRepository.delete(union);
    }

    @Override
    public void toggleActive(Long id) {
        Union union = unionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unión no encontrada"));

        union.setIsActive(!union.getIsActive());
        unionRepository.save(union);
    }
}