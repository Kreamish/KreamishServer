package com.kreamish.kream.sale.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PendingSaleDto {

    private String itemSizes;
    private Long price;
    private Long quantity;

    public static PendingSaleDto of(String itemSizes, Long price, Long quantity) {
        return new PendingSaleDto(itemSizes, price, quantity);
    }
}
