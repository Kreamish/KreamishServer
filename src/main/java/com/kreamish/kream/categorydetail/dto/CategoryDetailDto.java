package com.kreamish.kream.categorydetail.dto;

import com.kreamish.kream.category.dto.CategoryDto;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CategoryDetailDto {
    private Long categoryDetailId;
    private String name;
    private CategoryDto categoryDto;

    public static CategoryDetailDto of(CategoryDetail categoryDetail) {
        return new CategoryDetailDto(categoryDetail.getCategoryDetailId(), categoryDetail.getName(), CategoryDto.of(categoryDetail.getCategory()));
    }

    public boolean isBelongTo(CategoryDto categoryDto) {
        return this.categoryDto.getCategoryId().equals(categoryDto.getCategoryId());
    }
}
