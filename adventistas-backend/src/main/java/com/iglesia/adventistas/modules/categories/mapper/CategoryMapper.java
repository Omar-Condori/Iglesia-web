package com.iglesia.adventistas.modules.categories.mapper;

import com.iglesia.adventistas.modules.categories.dto.CategoryDTO;
import com.iglesia.adventistas.modules.categories.dto.CreateCategoryRequest;
import com.iglesia.adventistas.modules.categories.entity.Category;
import com.iglesia.adventistas.shared.util.SlugUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .color(category.getColor())
                .isActive(category.getIsActive())
                .sortOrder(category.getSortOrder())
                .createdAt(category.getCreatedAt())
                .build();
    }

    public Category toEntity(CreateCategoryRequest request) {
        if (request == null) return null;

        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(SlugUtils.generateSlug(request.getName()));
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        category.setIsActive(true);
        category.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);

        return category;
    }

    public void updateEntity(CreateCategoryRequest request, Category category) {
        if (request == null || category == null) return;

        category.setName(request.getName());
        category.setSlug(SlugUtils.generateSlug(request.getName()));
        category.setDescription(request.getDescription());
        category.setColor(request.getColor());
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }
    }
}