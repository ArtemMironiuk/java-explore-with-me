package ru.practicum.controller.notuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.service.CategoryService;


import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
@Validated
public class CategoryControllerPublic {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findCategories(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /categories");
        return categoryService.findCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto findCategoryById(@PathVariable @PositiveOrZero Long catId) {
        log.info("Получен запрос к эндпоинту GET, /categories/catId");
        return categoryService.findCategoryById(catId);
    }
}
