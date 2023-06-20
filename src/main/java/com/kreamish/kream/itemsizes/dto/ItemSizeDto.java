package com.kreamish.kream.itemsizes.dto;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ItemSizeDto {

    private String size;
    public static ItemSizeDto of(ItemSizes itemSizes) {
        return new ItemSizeDto(itemSizes.getSize());
    }
}
