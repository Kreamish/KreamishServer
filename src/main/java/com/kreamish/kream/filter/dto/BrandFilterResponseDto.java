package com.kreamish.kream.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.brand.dto.BrandDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

@Data
public class BrandFilterResponseDto {

    private final String brandFirstLetter;
    @JsonProperty("brandItems")
    private final List<BrandDto> brandDtoList;

    public static BrandFilterResponseDto of(Character brandFirstLetter, BrandDto brandDto) {
        return new BrandFilterResponseDto(brandFirstLetter.toString(),
            new ArrayList<>(Arrays.asList(brandDto)));
    }
}
