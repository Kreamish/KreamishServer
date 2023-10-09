package com.kreamish.kream.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.MultiValueMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ItemSizesFilterResponseDto {

    @JsonProperty("sizeSection")
    private final String category;

    @JsonProperty("sizeItems")
    private final MultiValueMap<String, String> sizes;

    public static ItemSizesFilterResponseDto of(String category,
        MultiValueMap<String, String> sizes) {
        return new ItemSizesFilterResponseDto(category, sizes);
    }

    public void sort() {
        sizes.forEach((key, valueList) -> Collections.sort(valueList));
    }
}
