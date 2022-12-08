package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryControllerByAdmin {

    private final CategoryService categoryService;

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
