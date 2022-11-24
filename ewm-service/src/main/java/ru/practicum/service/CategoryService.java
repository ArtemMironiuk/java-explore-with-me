package ru.practicum.service;

import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findCategories(Integer from, Integer size);

    CategoryDto findCategoryById(Long catId);

    CategoryDto createCategory(NewCategoryDto newCategory);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long categoryId);
}
