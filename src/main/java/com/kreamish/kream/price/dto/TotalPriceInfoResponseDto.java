package com.kreamish.kream.price.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.purchase.dto.SaleNowPriceDto;
import com.kreamish.kream.sale.dto.BuyNowPriceDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TotalPriceInfoResponseDto {

    @JsonProperty
    Long itemId;
    @JsonProperty
    Long itemSizesId;
    @JsonProperty
    RecentTradePriceInfoResponseDto recentTradePriceInfoResponseDto;
    @JsonProperty
    BuyNowPriceDto buyNowPriceDto;
    @JsonProperty
    SaleNowPriceDto saleNowPriceDto;

    public static TotalPriceInfoResponseDto of(Long itemId, Long itemSizesId,
        RecentTradePriceInfoResponseDto recentTradePriceInfo, BuyNowPriceDto buyNowPriceDto,
        SaleNowPriceDto saleNowPriceDto) {
        return new TotalPriceInfoResponseDto(itemId, itemSizesId, recentTradePriceInfo,
            buyNowPriceDto, saleNowPriceDto);
    }

}
