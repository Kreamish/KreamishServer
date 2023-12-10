package com.kreamish.kream.purchase.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.kreamish.kream.item.repository.TradeRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.purchase.dto.PurchaseRegisterResponseDto;
import com.kreamish.kream.purchase.repository.PurchaseRepository;
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
class PurchaseServiceImplTest {

    @InjectMocks
    PurchaseServiceImpl purchaseService;

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
    Sale sale;

    @Mock
    ItemSizes itemSizes;

    @Mock
    Member buyer;

    @Test
    @DisplayName("구매 입찰을 생성했을 때 마땅한 판매가 있다면, 기 판매 입찰 등록된 가격으로 거래가 성사되어야 한다.")
    void CREATE_PURCHASE_SHOULD_PROCEED_TRADE_IF_FIT_EXISTS() {
        long purchasePrice = 1000L;
        long salePrice = 950L;

        given(seller.getMemberId()).willReturn(1L);
        given(buyer.getMemberId()).willReturn(2L);

        given(memberRepository.findById(seller.getMemberId())).willReturn(Optional.of(seller));
        given(itemSizesRepository.findById(itemSizes.getItemSizesId())).willReturn(Optional.of(itemSizes));

        given(sale.getSalePrice()).willReturn(salePrice);
        given(sale.getMember()).willReturn(buyer);
        given(saleRepository.findMinPriceSaleByItemSizesId(itemSizes)).willReturn(Optional.of(sale));

        PurchaseRegisterResponseDto purchaseRegisterResponseDto = purchaseService.createPurchaseAndProceedTrade(
            seller.getMemberId(), itemSizes.getItemSizesId(), purchasePrice // 구매 가격
        );

        verify(tradeRepository).save(any());
        verify(sale).deal();

        assertThat(purchaseRegisterResponseDto.getTrade().getTradePrice()).isEqualTo(salePrice);
    }
}