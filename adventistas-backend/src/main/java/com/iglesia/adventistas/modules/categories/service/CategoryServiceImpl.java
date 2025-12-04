package com.iglesia.adventistas.modules.categories.service;

import com.iglesia.adventistas.modules.categories.dto.CategoryDTO;
import com.iglesia.adventistas.modules.categories.dto.CreateCategoryRequest;
import com.iglesia.adventistas.modules.categories.entity.Category;
import com.iglesia.adventistas.modules.categories.mapper.CategoryMapper;
import com.iglesia.adventistas.modules.categories.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        log.info("Creating category: {}", request.getName());

        Category category = categoryMapper.toEntity(request);

        // Generar slug único
        String baseSlug = com.iglesia.adventistas.shared.util.SlugUtils.generateSlug(request.getName());
        String finalSlug = baseSlug;
        int counter = 1;

        while (categoryRepository.existsBySlug(finalSlug)) {
            finalSlug = baseSlug + "-" + counter;
            counter++;
        }
        category.setSlug(finalSlug);

        Category saved = categoryRepository.save(category);

        return categoryMapper.toDTO(saved);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CreateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        categoryMapper.updateEntity(request, category);
        Category updated = categoryRepository.save(category);

        return categoryMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getActiveCategories() {
        return categoryRepository.findByIsActiveTrueOrderBySortOrder().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        categoryRepository.delete(category);
    }

    @Override
    public void toggleActive(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        category.setIsActive(!category.getIsActive());
        categoryRepository.save(category);
    }
}