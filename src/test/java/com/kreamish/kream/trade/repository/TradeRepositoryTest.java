package com.kreamish.kream.trade.repository;


import static com.kreamish.kream.datarunner.DefaultData.ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2;
import static com.kreamish.kream.datarunner.TradeData.TRADE4_WITH_PURCHASE4_SALE4;
import static com.kreamish.kream.datarunner.TradeData.TRADE5_WITH_PURCHASE5_SALE5;

import com.kreamish.kream.config.TestQueryDSLConfig;
import com.kreamish.kream.datarunner.DefaultData;
import com.kreamish.kream.datarunner.TradeData;
import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({TestQueryDSLConfig.class, DefaultData.class, TradeData.class})
@ActiveProfiles({"test", "default-data", "trade-data"})
class TradeRepositoryTest {

    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    JPAQueryFactory query;

    @Test
    @DisplayName("아이템 사이즈 아이디로 최근 거래가격 조회")
    void SUCCESS_FIND_RECENT_PRICES() {
        final Long itemId = ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2.getItemId();
        final Long recentPrice = TRADE5_WITH_PURCHASE5_SALE5.getTradePrice();
        final Long previousPrice = TRADE4_WITH_PURCHASE4_SALE4.getTradePrice();
        final List<Long> prices = List.of(recentPrice, previousPrice);

        final RecentTradePriceInfoResponseDto target = RecentTradePriceInfoResponseDto.of(prices);
        final RecentTradePriceInfoResponseDto source = tradeRepository.findTheLastTwoTradePricesByItemId(
            itemId);

        Assertions.assertThat(source.equals(target)).isTrue();
    }
}