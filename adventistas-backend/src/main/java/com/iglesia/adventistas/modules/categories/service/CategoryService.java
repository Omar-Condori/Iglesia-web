package com.iglesia.adventistas.modules.categories.service;

import com.iglesia.adventistas.modules.categories.dto.CategoryDTO;
import com.iglesia.adventistas.modules.categories.dto.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CreateCategoryRequest request);

    CategoryDTO updateCategory(Long id, CreateCategoryRequest request);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getActiveCategories();

    void deleteCategory(Long id);

    void toggleActive(Long id);
}