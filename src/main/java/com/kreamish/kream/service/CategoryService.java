package com.kreamish.kream.service;

import com.kreamish.kream.dto.CategoriesDto;
import com.kreamish.kream.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoriesDto getItems() {
        return CategoriesDto.of(categoryRepository.findAll());
    }
}
