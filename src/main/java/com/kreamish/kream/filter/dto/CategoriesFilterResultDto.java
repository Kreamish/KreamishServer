package com.kreamish.kream.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.category.dto.CategoriesDto;
import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import com.kreamish.kream.categorydetail.dto.CategoryDetailsDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoriesFilterResultDto {
    @JsonProperty("category_id")
    private Long id;
    @JsonProperty("category_name")
    private String name;
    private List<SimpleCategoryDetailDto> simpleCategoryDetailList;

    public static CategoriesFilterResultDto of(CategoryDto categoryDto, List<CategoryDetailDto> categoryDetailDtoList) {
        List<SimpleCategoryDetailDto> simpleCategoryDetailDtoList = categoryDetailDtoList.stream()
                .filter(categoryDetailDto -> categoryDetailDto.isBelongTo(categoryDto))
                .map(SimpleCategoryDetailDto::of)
                .collect(Collectors.toList());

        return new CategoriesFilterResultDto(categoryDto.getCategoryId(),categoryDto.getName(), simpleCategoryDetailDtoList);
    }
}
