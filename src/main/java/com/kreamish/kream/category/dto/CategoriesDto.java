package com.kreamish.kream.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.category.entity.Category;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CategoriesDto {

    @JsonProperty(value = "categories")
    List<CategoryDto> categoryDtoList;

    public static CategoriesDto of(List<Category> categories) {
        return new CategoriesDto(categories.stream()
            .map(CategoryDto::of)
            .collect(Collectors.toList()));
    }

    public boolean isEmpty() {
        return categoryDtoList.isEmpty();
    }
}
