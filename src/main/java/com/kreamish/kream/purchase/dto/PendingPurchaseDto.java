package com.kreamish.kream.purchase.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PendingPurchaseDto {

    private String itemSizes;
    private Long price;
    private Long quantity;

    public static PendingPurchaseDto of(String itemSizes, Long price, Long quantity) {
        return new PendingPurchaseDto(itemSizes, price, quantity);
    }
}
