package com.kreamish.kream.categorydetail.service;

import com.kreamish.kream.categorydetail.dto.CategoryDetailsDto;
import com.kreamish.kream.categorydetail.repository.CategoryDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryDetailService {

    private final CategoryDetailRepository categoryDetailRepository;

    public CategoryDetailsDto getAllCategoryDetails() {
        return CategoryDetailsDto.of(categoryDetailRepository.findAll());
    }
}
