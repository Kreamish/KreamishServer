package com.kreamish.kream.sale.repository;

import static com.kreamish.kream.TestDataRunner.ITEMSIZES_WITH_ITEM1;
import static com.kreamish.kream.TestDataRunner.MEMBER1;
import static org.assertj.core.api.Assertions.assertThat;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.sale.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"test", "data"})
class SaleQueryRepositoryImplTest {

    @Autowired
    SaleRepository saleRepository;

    ItemSizes itemSizes;
    Member seller;

    @BeforeEach
    public void beforeEach() {
        saleRepository.deleteAll();
        itemSizes = ITEMSIZES_WITH_ITEM1;
        seller = MEMBER1;
    }

    @Test
    @DisplayName("최소 금액의 판매를 가져오는 경우 단순 테스트")
    void FIND_MIN_PRICE_SALE_SHOULD_GET_MIN_PRICE_SIMPLE_CASE() {
        List<Sale> saleList = Arrays.asList(
            makeSale(10000L, DealStatus.PENDING),
            makeSale(20000L, DealStatus.PENDING),
            makeSale(9000L, DealStatus.PENDING),
            makeSale(15000L, DealStatus.PENDING)
        );
        long minPrice = saleList.stream()
            .mapToLong(Sale::getSalePrice).min().orElse(0L);

        Optional<Sale> sale =
            saleRepository.findMinPriceSaleByItemSizesId(itemSizes);

        assertThat(sale).isNotEmpty();
        assertThat(sale.get().getSalePrice()).isEqualTo(minPrice);
    }

    @Test
    @DisplayName("최소 금액의 판매를 가져오는 경우, PENDING 상태만 가져와야 한다.")
    void FIND_MIN_PRICE_SALE_SHOULD_NOT_RETURN_COMPLETE_DEAL_STATUS() {

        List<Sale> saleList = Arrays.asList(
            makeSale(20000L, DealStatus.PENDING),
            makeSale(10000L, DealStatus.PENDING),
            makeSale(9000L, DealStatus.COMPLETE),
            makeSale(15000L, DealStatus.PENDING)
        );

        long minPrice = saleList.stream()
            .filter(i -> DealStatus.PENDING.equals(i.getSaleStatus()))
            .mapToLong(Sale::getSalePrice).min().orElse(0L);

        Optional<Sale> sale =
            saleRepository.findMinPriceSaleByItemSizesId(itemSizes);

        assertThat(sale).isNotEmpty();
        assertThat(sale.get().getSalePrice()).isEqualTo(minPrice);
    }

    @Test
    void MIN_PRICE_SALE_SHOULD_EMPTY_IF_SALE_NOT_EXISTS() {
        Optional<Sale> sale =
            saleRepository.findMinPriceSaleByItemSizesId(itemSizes);

        assertThat(sale).isEmpty();
    }

    private Sale makeSale(Long price, DealStatus status) {
        return saleRepository.save(
            Sale.builder()
                .salePrice(price)
                .member(seller)
                .itemSizes(itemSizes)
                .saleStatus(status)
                .build()
        );
    }
}