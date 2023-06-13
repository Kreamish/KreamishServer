package com.kreamish.kream.filter.facade;

import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.category.service.CategoryService;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import com.kreamish.kream.categorydetail.service.CategoryDetailService;
import com.kreamish.kream.filter.dto.CategoriesFilterResultDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class FilterFacade {

    private final CategoryService categoryService;
    private final CategoryDetailService categoryDetailService;

    public List<CategoriesFilterResultDto> getCategories() {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories().getCategoryDtoList();
        List<CategoryDetailDto> categoryDetailDtoList = categoryDetailService.getAllCategoryDetails()
            .getCategoryDetailDtoList();

        return categoryDtoList.stream()
            .map(categoryDto -> CategoriesFilterResultDto.of(categoryDto, categoryDetailDtoList))
            .collect(Collectors.toList());
    }
}
