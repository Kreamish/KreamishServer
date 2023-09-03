package com.kreamish.kream.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoriesFilterResponseDto {

    @JsonProperty("category_id")
    private Long id;
    @JsonProperty("category_name")
    private String name;
    @JsonProperty("category_detail_list")
    private List<SimpleCategoryDetailDto> simpleCategoryDetailList;

    public static CategoriesFilterResponseDto of(CategoryDto categoryDto,
        List<CategoryDetailDto> categoryDetailDtoList) {
        List<SimpleCategoryDetailDto> simpleCategoryDetailDtoList = categoryDetailDtoList.stream()
            .filter(categoryDetailDto -> categoryDetailDto.isBelongTo(categoryDto))
            .map(SimpleCategoryDetailDto::of)
            .collect(Collectors.toList());

        return new CategoriesFilterResponseDto(categoryDto.getCategoryId(), categoryDto.getName(),
            simpleCategoryDetailDtoList);
    }
}
