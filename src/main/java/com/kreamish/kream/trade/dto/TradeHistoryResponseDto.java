package com.kreamish.kream.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TradeHistoryResponseDto {

    @JsonProperty
    private List<TradeOrderDto> tradeOrderDtoList;
    @JsonProperty
    private Long itemId;

    public static TradeHistoryResponseDto of(List<TradeOrderDto> tradeOrderDtoList, Long itemId) {
        return new TradeHistoryResponseDto(tradeOrderDtoList, itemId);
    }
}
