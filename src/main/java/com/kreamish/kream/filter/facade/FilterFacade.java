package com.kreamish.kream.filter.facade;

import com.kreamish.kream.category.dto.CategoriesDto;
import com.kreamish.kream.category.service.CategoryService;
import com.kreamish.kream.categorydetail.dto.CategoryDetailsDto;
import com.kreamish.kream.categorydetail.service.CategoryDetailService;
import com.kreamish.kream.filter.dto.CategoryDetailFilterResultDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterFacade {
    private final CategoryService categoryService;
    private final CategoryDetailService categoryDetailService;
    public CategoryDetailFilterResultDto getCategories() {
        CategoriesDto allCategories = categoryService.getAllCategories();
        CategoryDetailsDto allCategoryDetails = categoryDetailService.getAllCategoryDetails();

        return CategoryDetailFilterResultDto.of(allCategories, allCategoryDetails);
    }
}
