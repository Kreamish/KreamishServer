package com.kreamish.kream.categorydetail.service;

import com.kreamish.kream.categorydetail.dto.CategoryDetailsDto;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryDetailService {
    private final CategoryDetailRepository categoryDetailRepository;

    @Transactional(readOnly = true)
    public CategoryDetailsDto getAllCategoryDetails() {
        return CategoryDetailsDto.of(categoryDetailRepository.findAll());
    }
}
