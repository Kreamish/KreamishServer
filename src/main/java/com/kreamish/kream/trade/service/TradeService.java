package com.kreamish.kream.trade.service;

import com.kreamish.kream.item.dto.MarketPricesGraphResponseDto;
import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.trade.dto.TradeHistoryResponseDto;
import com.kreamish.kream.trade.enums.Period;

public interface TradeService {

    TradeHistoryResponseDto getTradeHistoryByItemId(Long itemId);

    RecentTradePriceInfoResponseDto getRecentTradePriceInfo(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo);

    MarketPricesGraphResponseDto getMarketPricesGraph(Long itemId, Period period);

    TradeHistoryResponseDto getTradeHistoryByItemSizesId(Long itemId, Long itemSizesId);
}
