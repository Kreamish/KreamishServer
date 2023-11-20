package com.kreamish.kream.purchase.service;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.item.repository.TradeRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.repository.ItemSizesRepository;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.repository.MemberRepository;
import com.kreamish.kream.purchase.dto.PurchaseDeleteResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseDetailResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseListResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterResponseDto;
import com.kreamish.kream.purchase.entity.Purchase;
import com.kreamish.kream.purchase.repository.PurchaseRepository;
import com.kreamish.kream.sale.entity.Sale;
import com.kreamish.kream.sale.repository.SaleRepository;
import com.kreamish.kream.trade.dto.TradeResponseDto;
import com.kreamish.kream.trade.entity.Trade;
import jakarta.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final SaleRepository saleRepository;

    private final MemberRepository memberRepository;

    private final ItemSizesRepository itemSizesRepository;

    private final TradeRepository tradeRepository;

    @Override
    @Transactional
    public PurchaseRegisterResponseDto createPurchaseAndProceedTrade(Long memberId, Long itemSizesId, Long price) {

        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> (new NoSuchElementException("member not found -> " + memberId))
        );

        ItemSizes itemSizes = itemSizesRepository.findById(itemSizesId).orElseThrow(
            () -> (new NoSuchElementException("item-sizes not found -> " + itemSizesId))
        );

        Purchase purchase = Purchase.builder()
            .member(member)
            .purchasePrice(price)
            .purchaseStatus(DealStatus.PENDING)
            .itemSizes(itemSizes)
            .build();

        purchaseRepository.save(purchase);

        Optional<Sale> optionalSale = getFitSale(itemSizes, price);

        if (optionalSale.isEmpty()) {
            return new PurchaseRegisterResponseDto(
                purchase.getPurchaseId()
            );
        }

        Sale sale = optionalSale.get();

        Trade trade = Trade.builder()
            .purchase(purchase)
            .sale(sale)
            .tradePrice(sale.getSalePrice())
            .build();

        sale.deal();
        purchase.deal();

        saleRepository.save(sale);
        tradeRepository.save(trade);

        return new PurchaseRegisterResponseDto(
            purchase.getPurchaseId(),
            TradeResponseDto.builder()
                .tradeId(trade.getTradeId())
                .purchaseId(purchase.getPurchaseId())
                .saleId(trade.getSale().getSaleId())
                .tradePrice(trade.getTradePrice())
                .tradeDate(trade.getCreatedAt())
                .build()
        );
    }

    private Optional<Sale> getFitSale(ItemSizes itemSizes, Long purchasePrice) {
        Optional<Sale> optionalSale = saleRepository.findMinPriceSaleByItemSizesId(itemSizes);
        if (optionalSale.isEmpty() || optionalSale.get().getSalePrice() > purchasePrice) {
            return Optional.empty();
        } else {
            return optionalSale;
        }
    }

    @Override
    public PurchaseDeleteResponseDto withdrawPurchase(Long memberId, Long purchasesId) {
        Purchase purchase = purchaseRepository.findById(purchasesId)
            .orElseThrow(() -> new NoSuchElementException("Not Found Purchase by purchaseId"));

        purchaseRepository.deleteById(purchase.getPurchaseId());

        return new PurchaseDeleteResponseDto(
            purchasesId,
            memberId,
            null,
            null
        );
    }

    @Override
    public PurchaseListResponseDto findPurchasesByMemberId(Long memberId, @Nullable Boolean isCompleteStatus) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException("Not Fount Member By MemberId"));

        PurchaseListResponseDto responseDto = new PurchaseListResponseDto();
        responseDto.setPurchases(purchaseRepository.findByMember(member, isCompleteStatus)
            .stream()
            .map(i -> PurchaseDetailResponseDto.builder()
                .purchaseId(i.getPurchaseId())
                .itemSizesId(i.getItemSizes().getItemSizesId())
                .memberId(i.getMember().getMemberId())
                .purchasePrice(i.getPurchasePrice())
                .status(i.getPurchaseStatus())
                .build())
            .toList()
        );

        return responseDto;
    }

    @Override
    public PurchaseListResponseDto findPurchasesByItemSizesId(Long memberId, @Nullable Boolean isCompleteStatus) {
        return null;
    }
}
