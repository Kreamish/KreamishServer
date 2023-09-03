package com.kreamish.kream.itemsizes.dto;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
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
public class ItemSizeDto {

    private String size;
    private String category;

    public static ItemSizeDto of(ItemSizes itemSizes) {
        return new ItemSizeDto(itemSizes.getSize(), itemSizes.getItem().getCategory().getName());
    }
}
