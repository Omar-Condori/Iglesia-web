package com.iglesia.adventistas.modules.departments.service;

import com.iglesia.adventistas.modules.departments.dto.CreateContentRequest;
import com.iglesia.adventistas.modules.departments.dto.DepartmentContentDTO;
import com.iglesia.adventistas.modules.departments.entity.DepartmentContent;
import com.iglesia.adventistas.modules.departments.entity.DepartmentSection;
import com.iglesia.adventistas.modules.departments.mapper.DepartmentContentMapper;
import com.iglesia.adventistas.modules.departments.repository.DepartmentContentRepository;
import com.iglesia.adventistas.modules.departments.repository.DepartmentSectionRepository;
import com.iglesia.adventistas.modules.users.entity.User;
import com.iglesia.adventistas.modules.users.repository.UserRepository;
import com.iglesia.adventistas.shared.exception.ResourceNotFoundException;
import com.iglesia.adventistas.shared.util.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentContentServiceImpl implements DepartmentContentService {

    private final DepartmentContentRepository contentRepository;
    private final DepartmentSectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final DepartmentContentMapper contentMapper;

    @Override
    @Transactional
    public DepartmentContentDTO createContent(Long sectionId, CreateContentRequest request, Long authorId) {
        DepartmentSection section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        DepartmentContent content = DepartmentContent.builder()
                .section(section)
                .title(request.getTitle())
                .slug(SlugUtils.generateSlug(request.getTitle()))
                .content(request.getContent())
                .excerpt(request.getExcerpt())
                .featuredImage(request.getFeaturedImage())
                .author(author)
                .isPublished(request.getIsPublished() != null ? request.getIsPublished() : false)
                .publishedAt(request.getIsPublished() ? LocalDateTime.now() : null)
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .views(0)
                .build();

        content = contentRepository.save(content);
        return contentMapper.toDTO(content);
    }

    @Override
    @Transactional
    public DepartmentContentDTO updateContent(Long id, CreateContentRequest request) {
        DepartmentContent content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado"));

        content.setTitle(request.getTitle());
        content.setSlug(SlugUtils.generateSlug(request.getTitle()));
        content.setContent(request.getContent());
        content.setExcerpt(request.getExcerpt());
        content.setFeaturedImage(request.getFeaturedImage());
        content.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : content.getSortOrder());

        if (request.getIsPublished() != null && request.getIsPublished() && !content.getIsPublished()) {
            content.setPublishedAt(LocalDateTime.now());
        }
        content.setIsPublished(request.getIsPublished() != null ? request.getIsPublished() : content.getIsPublished());

        content = contentRepository.save(content);
        return contentMapper.toDTO(content);
    }

    @Override
    @Transactional
    public void deleteContent(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contenido no encontrado");
        }
        contentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentContentDTO getContentById(Long id) {
        DepartmentContent content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado"));
        return contentMapper.toDTO(content);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentContentDTO> getContentBySection(Long sectionId, boolean publishedOnly) {
        List<DepartmentContent> contents;

        if (publishedOnly) {
            contents = contentRepository.findBySectionIdAndIsPublishedTrueOrderBySortOrder(sectionId);
        } else {
            contents = contentRepository.findBySectionIdOrderBySortOrder(sectionId);
        }

        return contents.stream()
                .map(contentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void togglePublish(Long id) {
        DepartmentContent content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenido no encontrado"));

        content.setIsPublished(!content.getIsPublished());
        if (content.getIsPublished()) {
            content.setPublishedAt(LocalDateTime.now());
        }

        contentRepository.save(content);
    }
}
