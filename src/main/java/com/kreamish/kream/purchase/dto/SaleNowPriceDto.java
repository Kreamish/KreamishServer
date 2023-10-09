package com.kreamish.kream.purchase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaleNowPriceDto {

    @JsonProperty
    private final Long purchaseId;
    @JsonProperty
    private final Long purchasePrice;

    public static SaleNowPriceDto empty() {
        return new SaleNowPriceDto(0L, 0L);
    }
}
