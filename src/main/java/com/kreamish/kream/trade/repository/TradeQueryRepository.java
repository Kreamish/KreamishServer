package com.kreamish.kream.trade.repository;

import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.trade.dto.TradeOrderDto;
import com.kreamish.kream.trade.enums.Period;
import java.util.List;

public interface TradeQueryRepository {

    List<TradeOrderDto> findAllByItemId(Long itemId);

    RecentTradePriceInfoResponseDto findTheLastTwoTradePricesByItemSizesId(Long itemSizesId);

    RecentTradePriceInfoResponseDto findTheLastTwoTradePricesByItemId(Long itemId);

    List<TradeOrderDto> findAllByItemIdAndCreatedAtForTheLastNMonths(Long itemId, Period period);

    List<TradeOrderDto> findAllByItemSizesId(Long itemSizesId);
}
