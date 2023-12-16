package com.kreamish.kream.trade.service;

import com.kreamish.kream.item.dto.MarketPricesGraphResponseDto;
import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.trade.dto.TradeHistoryResponseDto;
import com.kreamish.kream.trade.dto.TradeOrderDto;
import com.kreamish.kream.trade.enums.Period;
import com.kreamish.kream.trade.repository.TradeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public TradeHistoryResponseDto getTradeHistoryByItemId(Long itemId) {
        List<TradeOrderDto> tradeOrderDtoList = tradeRepository.findAllByItemId(itemId);

        return TradeHistoryResponseDto.of(tradeOrderDtoList, itemId);
    }

    @Override
    public TradeHistoryResponseDto getTradeHistoryByItemSizesId(Long itemId, Long itemSizesId) {
        List<TradeOrderDto> tradeOrderDtoList = tradeRepository.findAllByItemSizesId(itemSizesId);

        return TradeHistoryResponseDto.of(tradeOrderDtoList, itemId);
    }

    @Override
    public RecentTradePriceInfoResponseDto getRecentTradePriceInfo(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo) {
        // 미 로그인 유저 or 로그인 유저 and 모든 아이템 사이즈
        if (loginMemberInfo.isNotLoggedIn() || (!loginMemberInfo.isNotLoggedIn()
            && itemSizesId == null)) {
            return tradeRepository.findTheLastTwoTradePricesByItemId(itemId);
        }
        // 로그인 유저 and 특정 아이템 사이즈
        return tradeRepository.findTheLastTwoTradePricesByItemSizesId(itemSizesId);
    }

    @Override
    public MarketPricesGraphResponseDto getMarketPricesGraph(Long itemId, Period period) {
        List<TradeOrderDto> tradeList = tradeRepository.findAllByItemIdAndCreatedAtForTheLastNMonths(
            itemId, period);
        return MarketPricesGraphResponseDto.of(tradeList, itemId);
    }
}
