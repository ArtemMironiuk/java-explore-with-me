package ru.practicum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.service.CategoryService;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryControllerByAdmin {

    @Autowired
    private CategoryService categoryService;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Получен запрос к эндпоинту PATCH, /admin/categories");
        return categoryService.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategory) {
        log.info("Получен запрос к эндпоинту POST, /admin/categories");
        return categoryService.createCategory(newCategory);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable @Positive Long categoryId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/categories/{categoryId}");
        categoryService.deleteCategory(categoryId);
    }
}
