package com.kreamish.kream.itemsizes.dto;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SimpleItemSizesDto {

    private final Long itemSizesId;
    private final String size;

    public static SimpleItemSizesDto of(ItemSizes itemSizes) {
        return new SimpleItemSizesDto(itemSizes.getItemSizesId(), itemSizes.getSize());
    }
}
