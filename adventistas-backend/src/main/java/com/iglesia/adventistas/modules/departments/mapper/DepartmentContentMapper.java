package com.iglesia.adventistas.modules.departments.mapper;

import com.iglesia.adventistas.modules.departments.dto.DepartmentContentDTO;
import com.iglesia.adventistas.modules.departments.entity.DepartmentContent;
import org.springframework.stereotype.Component;

@Component
public class DepartmentContentMapper {

    public DepartmentContentDTO toDTO(DepartmentContent content) {
        if (content == null) {
            return null;
        }

        return DepartmentContentDTO.builder()
                .id(content.getId())
                .sectionId(content.getSection().getId())
                .sectionName(content.getSection().getName())
                .departmentName(content.getSection().getDepartment().getName())
                .title(content.getTitle())
                .slug(content.getSlug())
                .content(content.getContent())
                .excerpt(content.getExcerpt())
                .featuredImage(content.getFeaturedImage())
                .authorEmail(content.getAuthor() != null ? content.getAuthor().getEmail() : null)
                .authorId(content.getAuthor() != null ? content.getAuthor().getId() : null)
                .isPublished(content.getIsPublished())
                .publishedAt(content.getPublishedAt())
                .sortOrder(content.getSortOrder())
                .views(content.getViews())
                .createdAt(content.getCreatedAt())
                .updatedAt(content.getUpdatedAt())
                .build();
    }
}
