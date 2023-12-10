package com.kreamish.kream.sale.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.item.repository.TradeRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.purchase.entity.Purchase;
import com.kreamish.kream.purchase.repository.PurchaseRepository;
import com.kreamish.kream.sale.dto.SaleRegisterResponseDto;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @InjectMocks
    SaleServiceImpl saleServiceImpl;

    @Mock
    SaleRepository saleRepository;

    @Mock
    ItemSizesRepository itemSizesRepository;

    @Mock
    TradeRepository tradeRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    Member seller;

    @Mock
    Member buyer;

    @Mock
    Purchase purchase;

    @Mock
    ItemSizes itemSizes;

    @Test
    @DisplayName("판매 입찰을 생성했을 때 마땅한 판매가 있다면, 기 구매 입찰 등록된 가격으로 거래가 성사되어야 한다.")
    void CREATE_SALE_SHOULD_PROCEED_TRADE_IF_FIT_EXISTS() {
        long salePrice = 950L;
        long purchasePrice = 1000L;

        given(seller.getMemberId()).willReturn(1L);
        given(buyer.getMemberId()).willReturn(2L);

        given(memberRepository.findById(seller.getMemberId())).willReturn(Optional.of(seller));
        given(itemSizesRepository.findById(itemSizes.getItemSizesId())).willReturn(Optional.of(itemSizes));

        given(purchase.getPurchasePrice()).willReturn(purchasePrice);
        given(purchase.getMember()).willReturn(buyer);
        given(purchaseRepository.findMaxPricePurchaseByItemSizesId(itemSizes)).willReturn(Optional.of(purchase));

        SaleRegisterResponseDto saleRegisterResponseDto = saleServiceImpl.createSaleAndProceedTrade(
            seller.getMemberId(), itemSizes.getItemSizesId(), salePrice // 구매 가격
        );

        verify(tradeRepository).save(any());
        verify(purchase).deal();

        assertThat(saleRegisterResponseDto.getTrade().getTradePrice()).isEqualTo(purchasePrice);
    }

    @Test
    @DisplayName("본인이 등록한 판매 입찰은 본인이 등록한 구매 입찰과 거래가 성사되지 않아야 한다.") //TODO
    void CREATE_SALE_SHOULD_THROWS_EXCEPTION() {
        long salePrice = 950L;
        long purchasePrice = 1000L;

        given(memberRepository.findById(seller.getMemberId())).willReturn(Optional.of(seller));
        given(itemSizesRepository.findById(itemSizes.getItemSizesId())).willReturn(Optional.of(itemSizes));

        given(purchase.getPurchasePrice()).willReturn(purchasePrice);
        given(purchase.getMember()).willReturn(seller);
        given(purchaseRepository.findMaxPricePurchaseByItemSizesId(itemSizes)).willReturn(Optional.of(purchase));

        assertThrows(IllegalArgumentException.class, () -> saleServiceImpl.createSaleAndProceedTrade(
            seller.getMemberId(), itemSizes.getItemSizesId(), salePrice // 구매 가격
        ));
    }

    @Test
    @DisplayName("PENDING 상태가 아닌 sale 삭제는 예외가 발생해야 한다.")
    void TRYING_DELETE_NON_PENDING_STATUS_SALE_SHOULD_THROWS_EXCEPTION() {
        Long memberId = 12345L;
        Long saleId = 9234L;

        Member seller = Member.builder()
            .memberId(memberId)
            .build();

        Sale sale = Sale.builder()
            .saleStatus(DealStatus.COMPLETE)
            .member(seller)
            .saleId(saleId)
            .itemSizes(itemSizes)
            .build();

        given(saleRepository.findById(saleId)).willReturn(Optional.of(sale));
        given(memberRepository.findById(memberId)).willReturn(Optional.of(seller));

        assertThrows(IllegalStateException.class,
            () -> saleServiceImpl.withdrawSale(memberId, saleId));
    }
}

