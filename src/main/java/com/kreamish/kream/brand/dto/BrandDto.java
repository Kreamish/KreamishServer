package com.kreamish.kream.brand.dto;

import com.kreamish.kream.brand.entity.Brand;
import lombok.Data;

@Data
public class BrandDto {

    private final Long id;
    private final String name;

    public static BrandDto of(Brand brand) {
        return new BrandDto(brand.getBrandId(), brand.getName());
    }

    public Character getFirstLetter() {
        return Character.toLowerCase(name.charAt(0));
    }
}
