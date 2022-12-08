package ru.practicum.utils.mapper;

import ru.practicum.model.Category;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(NewCategoryDto newCategory) {
        return Category.builder()
                .name(newCategory.getName())
                .build();
    }
}
