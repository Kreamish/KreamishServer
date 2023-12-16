package com.kreamish.kream.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.trade.dto.TradeOrderDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketPricesGraphResponseDto {

    @JsonProperty
    private List<TradeOrderDto> tradeOrderDtoList;
    @JsonProperty
    private Long itemId;

    public static MarketPricesGraphResponseDto of(List<TradeOrderDto> tradeOrderDtoList,
        Long itemId) {
        return new MarketPricesGraphResponseDto(tradeOrderDtoList, itemId);
    }
}
