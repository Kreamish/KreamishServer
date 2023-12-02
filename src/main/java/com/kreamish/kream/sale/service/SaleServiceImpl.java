package com.kreamish.kream.sale.service;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.item.repository.TradeRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.purchase.dto.PurchaseDetailResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseListResponseDto;
import com.kreamish.kream.purchase.entity.Purchase;
import com.kreamish.kream.purchase.repository.PurchaseRepository;
import com.kreamish.kream.sale.dto.SaleDetailResponseDto;
import com.kreamish.kream.sale.dto.SaleListResponseDto;
import com.kreamish.kream.sale.dto.SaleRegisterResponseDto;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import com.kreamish.kream.trade.dto.TradeResponseDto;
import com.kreamish.kream.trade.entity.Trade;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleServiceImpl implements SaleService {

    private final MemberRepository memberRepository;

    private final ItemSizesRepository itemSizesRepository;

    private final SaleRepository saleRepository;

    private final TradeRepository tradeRepository;

    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public SaleRegisterResponseDto createSaleAndProceedTrade(Long memberId, Long itemSizesId, Long price) {

        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> (new NoSuchElementException("member not found -> " + memberId))
        );

        ItemSizes itemSizes = itemSizesRepository.findById(itemSizesId).orElseThrow(
            () -> (new NoSuchElementException("item-sizes not found -> " + itemSizesId))
        );

        Sale sale = Sale.builder()
            .member(member)
            .salePrice(price)
            .saleStatus(DealStatus.PENDING)
            .itemSizes(itemSizes)
            .build();

        saleRepository.save(sale);

        Optional<Purchase> optionalPurchase = getFitPurchase(itemSizes, price);

        if (optionalPurchase.isEmpty()) {
            return new SaleRegisterResponseDto(
                sale.getSaleId()
            );
        }

        Purchase purchase = optionalPurchase.get();

        Trade trade = Trade.builder()
            .purchase(purchase)
            .sale(sale)
            .tradePrice(purchase.getPurchasePrice())
            .build();

        purchase.deal();
        sale.deal();

        purchaseRepository.save(purchase);
        tradeRepository.save(trade);

        return new SaleRegisterResponseDto(
            sale.getSaleId(),
            TradeResponseDto.builder()
                .tradeId(trade.getTradeId())
                .purchaseId(trade.getPurchase().getPurchaseId())
                .saleId(sale.getSaleId())
                .tradePrice(trade.getTradePrice())
                .tradeDate(trade.getCreatedAt())
                .build()
        );
    }

    @Override
    public SaleListResponseDto findSalesByMemberId(Long memberId, Boolean isComplete) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException("Not Fount Member By MemberId"));

        return new SaleListResponseDto(findSaleList(member, isComplete));

    }

    private Optional<Purchase> getFitPurchase(ItemSizes itemSizes, Long salePrice) {
        Optional<Purchase> optionalSale = purchaseRepository.findMaxPricePurchaseByItemSizesId(itemSizes);
        if (optionalSale.isEmpty() || optionalSale.get().getPurchasePrice() < salePrice) {
            return Optional.empty();
        } else {
            return optionalSale;
        }
    }

    private List<SaleDetailResponseDto> findSaleList(Member member, Boolean isComplete) {
        return saleRepository.findByMember(member, isComplete).stream()
            .map(this::convertToSaleDetailResponseDto)
            .toList();
    }

    private SaleDetailResponseDto convertToSaleDetailResponseDto(Sale sale) {
        return SaleDetailResponseDto.builder()
            .saleId(sale.getSaleId())
            .itemSizesId(sale.getItemSizes().getItemSizesId())
            .memberId(sale.getMember().getMemberId())
            .salePrice(sale.getSalePrice())
            .status(sale.getSaleStatus())
            .build();
    }
}
