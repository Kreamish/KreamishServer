package com.kreamish.kream.category.service;

import com.kreamish.kream.category.dto.CategoriesDto;
import com.kreamish.kream.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoriesDto getAllCategories() {
        return CategoriesDto.of(categoryRepository.findAll());
    }
}
