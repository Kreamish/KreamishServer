package com.kreamish.kream.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.itemsizes.dto.SimpleItemSizesDto;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LikedItemSizesResponseDto {

    private final Long itemId;
    @JsonProperty("likedItemSizeList")
    private final List<SimpleItemSizesDto> simpleItemSizesDtoList;

    public static LikedItemSizesResponseDto of(List<ItemSizes> likedItemSizes,
        Long itemId) {
        List<SimpleItemSizesDto> collect = likedItemSizes.stream().map(SimpleItemSizesDto::of)
            .collect(Collectors.toList());
        return new LikedItemSizesResponseDto(itemId, collect);
    }
}
