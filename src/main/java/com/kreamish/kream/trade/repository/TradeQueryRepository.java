package com.kreamish.kream.trade.repository;

import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.trade.entity.Trade;
import com.kreamish.kream.trade.enums.Period;
import java.util.List;

public interface TradeQueryRepository {

    RecentTradePriceInfoResponseDto findTheLastTwoTradePricesByItemSizesId(Long itemSizesId);

    RecentTradePriceInfoResponseDto findTheLastTwoTradePricesByItemId(Long itemId);

    List<Trade> findAllByItemIdAndCreatedAtMoreOrEqualThan(long itemId, Period period);
}
