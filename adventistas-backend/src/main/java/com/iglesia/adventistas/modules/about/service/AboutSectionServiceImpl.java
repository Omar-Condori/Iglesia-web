package com.iglesia.adventistas.modules.about.service;

import com.iglesia.adventistas.modules.about.dto.AboutSectionDTO;
import com.iglesia.adventistas.modules.about.dto.UpdateAboutSectionRequest;
import com.iglesia.adventistas.modules.about.entity.AboutSection;
import com.iglesia.adventistas.modules.about.mapper.AboutSectionMapper;
import com.iglesia.adventistas.modules.about.repository.AboutSectionRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AboutSectionServiceImpl implements AboutSectionService {

    private final AboutSectionRepository repository;
    private final AboutSectionMapper mapper;

    @Override
    public List<AboutSectionDTO> getAllActiveSections() {
        return repository.findAllByIsActiveTrueOrderBySortOrder()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AboutSectionDTO getSectionBySlug(String slug) {
        AboutSection section = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada: " + slug));
        return mapper.toDTO(section);
    }

    @Override
    @Transactional
    public AboutSectionDTO updateSection(Long id, UpdateAboutSectionRequest request) {
        AboutSection section = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + id));

        section.setTitle(request.getTitle());
        section.setContent(request.getContent());
        section.setIcon(request.getIcon());
        section.setSortOrder(request.getSortOrder());
        section.setIsActive(request.getIsActive());

        AboutSection updated = repository.save(section);
        return mapper.toDTO(updated);
    }
}
