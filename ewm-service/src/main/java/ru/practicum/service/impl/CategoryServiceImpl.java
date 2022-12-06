package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ExistsElementException;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.model.Category;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.category.NewCategoryDto;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.service.CategoryService;
import ru.practicum.utils.mapper.CategoryMapper;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(toList());
    }

    @Override
    public CategoryDto findCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ObjectNotFoundException("Category c таким id нет в базе"));
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public CategoryDto createCategory(NewCategoryDto newCategory) {
        if (categoryRepository.countByName(newCategory.getName()) != 0) {
            throw new ExistsElementException("Category with this name already exist");
        }
        @Valid Category category = categoryRepository.save(CategoryMapper.toCategory(newCategory));
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (categoryRepository.countByName(categoryDto.getName()) != 0) {
            throw new ExistsElementException("Category with this name already exist");
        }
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Category c таким id нет в базе"));
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой категории!"));
        categoryRepository.deleteById(categoryId);
    }
}
