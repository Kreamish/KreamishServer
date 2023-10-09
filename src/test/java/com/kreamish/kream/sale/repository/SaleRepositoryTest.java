package com.kreamish.kream.sale.repository;

import static com.kreamish.kream.datarunner.DefaultData.ITEM_SIZES1_WITH_ITEM1;
import static com.kreamish.kream.datarunner.TradeData.SALE6_NOT_TRADED_AND_THE_LOWEST_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kreamish.kream.config.TestQueryDSLConfig;
import com.kreamish.kream.datarunner.DefaultData;
import com.kreamish.kream.datarunner.TradeData;
import com.kreamish.kream.sale.entity.Sale;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({TestQueryDSLConfig.class, DefaultData.class, TradeData.class})
@ActiveProfiles({"test", "default-data", "trade-data"})
class SaleRepositoryTest {

    @Autowired
    SaleRepository saleRepository;

    @Test
    @DisplayName("아이템 아이디로 즉시 구매 가격 찾기. 판매 입찰 중에서 제일 낮은 가격")
    void FIND_BUY_NOW_SHOULD_BE_SALE6() {
        //given
        final Long item1Id = ITEM_SIZES1_WITH_ITEM1.getItem().getItemId();
        final Sale targetSale = SALE6_NOT_TRADED_AND_THE_LOWEST_PRICE;

        //when
        final Sale srcSale = saleRepository.findTheLowestPriceByItemId(item1Id).get();

        //then
        assertThat(srcSale.getSaleId()).isEqualTo(targetSale.getSaleId());
    }

    @Test
    @DisplayName("아이템 아이디로 즉시 구매 가격 찾기. 존재하지 않는 아이템 ID")
    void FIND_BUY_NOW_SHOULD_THROW_EXCEPTION() {
        final Long notExistId = Long.MAX_VALUE;

        Optional<Sale> theLowestPriceByItemId = saleRepository.findTheLowestPriceByItemId(
            notExistId);

        assertThrows(NoSuchElementException.class, () -> theLowestPriceByItemId.get());
    }

    @Test
    @DisplayName("아이템 사이즈 아이디로 즉시 구매 가격 찾기.")
    void FIND_BUY_NOW_SHOULD_BE_EXISTED() {
        //given
        final Long itemSizesId = SALE6_NOT_TRADED_AND_THE_LOWEST_PRICE.getItemSizes()
            .getItemSizesId();
        final Sale targetSale = SALE6_NOT_TRADED_AND_THE_LOWEST_PRICE;

        //when
        Optional<Sale> theLowestPriceByItemSizesId = saleRepository.findTheLowestPriceByItemSizesId(
            itemSizesId);

        //then
        Assertions.assertDoesNotThrow(() -> theLowestPriceByItemSizesId.get());
        assertThat(theLowestPriceByItemSizesId.get().getSaleId()).isEqualTo(targetSale.getSaleId());
    }
}