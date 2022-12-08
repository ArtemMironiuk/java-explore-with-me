package ru.practicum.service;

import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    /**
     * Получение категорий
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     */
    List<CategoryDto> findCategories(Integer from, Integer size);

    /**
     * Получение информации о категории по ее идентификатору
     */
    CategoryDto findCategoryById(Long catId);

    /**
     * Добавление новой категории
     */
    CategoryDto createCategory(NewCategoryDto newCategory);

    /**
     * Изменение категории
     */
    CategoryDto updateCategory(CategoryDto categoryDto);

    /**
     * Удаление категории
     */
    void deleteCategory(Long categoryId);
}
