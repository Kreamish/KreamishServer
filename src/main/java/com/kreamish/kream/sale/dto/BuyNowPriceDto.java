package com.kreamish.kream.sale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.sale.entity.Sale;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuyNowPriceDto {

    @JsonProperty
    private final Long saleId;
    @JsonProperty
    private final Long salePrice;

    public static BuyNowPriceDto empty() {
        return new BuyNowPriceDto(0L, 0L);
    }

    public static BuyNowPriceDto of(Sale sale) {
        return new BuyNowPriceDto(sale.getSaleId(), sale.getSalePrice());
    }
}
