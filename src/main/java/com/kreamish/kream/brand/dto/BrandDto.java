package com.kreamish.kream.brand.dto;

import com.kreamish.kream.brand.entity.Brand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BrandDto {

    private Long id;
    private String name;

    public static BrandDto of(Brand brand) {
        return new BrandDto(brand.getBrandId(), brand.getName());
    }
}
