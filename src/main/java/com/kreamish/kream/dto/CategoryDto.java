package com.kreamish.kream.dto;

import com.kreamish.kream.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoryDto {

    private String name;

    public static CategoryDto of(Category category) {
        return new CategoryDto(category.getName());
    }
}
