package com.kreamish.kream.dto;

import com.kreamish.kream.entity.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoriesDto {

    List<CategoryDto> categoryDtoList;

    public static CategoriesDto of(List<Category> Categories) {
        return new CategoriesDto(Categories.stream()
            .map(CategoryDto::of)
            .collect(Collectors.toList()));
    }

    public boolean isEmpty() {
        return categoryDtoList.isEmpty();
    }
}
