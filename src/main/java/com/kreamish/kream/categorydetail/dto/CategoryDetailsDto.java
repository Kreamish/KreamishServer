package com.kreamish.kream.categorydetail.dto;

import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.categorydetail.dto.CategoryDetailDto;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CategoryDetailsDto {
    List<CategoryDetailDto> categoryDetailDtoList;

    public static CategoryDetailsDto of(List<CategoryDetail> categoryDetailList) {
        return new CategoryDetailsDto(categoryDetailList.stream()
                .map(CategoryDetailDto::of)
                .collect(Collectors.toList()));
    }
}
