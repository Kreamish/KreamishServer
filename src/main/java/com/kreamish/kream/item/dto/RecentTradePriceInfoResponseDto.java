package com.kreamish.kream.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class RecentTradePriceInfoResponseDto {

    @JsonProperty
    private final Long recentPrice;
    @JsonProperty
    private final Long previousRecentPrice;
    @JsonProperty
    private final Float priceChangeRatio;

    public static RecentTradePriceInfoResponseDto of(List<Long> lastTwoTradePrices) {
        Long recentPrice = 0L;
        Long previousRecentPrice = 0L;
        Float priceChangeRatio = 0F;

        if (!lastTwoTradePrices.isEmpty()) {
            recentPrice = lastTwoTradePrices.get(0);
        }
        if (lastTwoTradePrices.size() == 2) {
            previousRecentPrice = lastTwoTradePrices.get(1);
            priceChangeRatio =
                (recentPrice - previousRecentPrice) / previousRecentPrice.floatValue();
        }

        return new RecentTradePriceInfoResponseDto(recentPrice, previousRecentPrice,
            priceChangeRatio);
    }
}
