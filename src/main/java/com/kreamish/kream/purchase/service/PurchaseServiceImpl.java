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
import java.util.List;
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
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException("Not Found Member By MemberId"));

        Purchase purchase = purchaseRepository.findById(purchasesId)
            .orElseThrow(() -> new NoSuchElementException("Not Found Purchase by purchaseId"));

        if (!purchase.getMember().getMemberId().equals(member.getMemberId())) {
            throw new IllegalStateException("Purchase is not registered by current member");
        }
        dealStatusShouldPending(purchase.getPurchaseStatus());

        Long itemSizesId = purchase.getItemSizes().getItemSizesId();
        Long beforePrice = purchase.getPurchasePrice();

        purchaseRepository.deleteById(purchase.getPurchaseId());

        return new PurchaseDeleteResponseDto(
            purchasesId,
            memberId,
            itemSizesId,
            beforePrice
        );
    }

    @Override
    public PurchaseListResponseDto findPurchasesByMemberId(Long memberId, @Nullable Boolean isComplete) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException("Not Fount Member By MemberId"));

        return new PurchaseListResponseDto(findPurchaseList(member, isComplete));
    }

    @Override
    public PurchaseListResponseDto findPurchasesByItemSizesId(Long itemSizesId, @Nullable Boolean isComplete) {
        ItemSizes itemSizes = itemSizesRepository.findById(itemSizesId)
            .orElseThrow(() -> new NoSuchElementException("Not Found ItemSizes By ItemSizesId"));

        return new PurchaseListResponseDto(findPurchaseList(itemSizes, isComplete));
    }

    private List<PurchaseDetailResponseDto> findPurchaseList(ItemSizes itemSizes, Boolean isComplete) {
        return purchaseRepository.findByItemSizes(itemSizes, isComplete).stream()
            .map(this::convertToPurchaseDetailResponseDto)
            .toList();
    }

    private List<PurchaseDetailResponseDto> findPurchaseList(Member member, Boolean isComplete) {
        return purchaseRepository.findByMember(member, isComplete).stream()
            .map(this::convertToPurchaseDetailResponseDto)
            .toList();
    }

    private PurchaseDetailResponseDto convertToPurchaseDetailResponseDto(Purchase purchase) {
        return PurchaseDetailResponseDto.builder()
            .purchaseId(purchase.getPurchaseId())
            .itemSizesId(purchase.getItemSizes().getItemSizesId())
            .memberId(purchase.getMember().getMemberId())
            .purchasePrice(purchase.getPurchasePrice())
            .status(purchase.getPurchaseStatus())
            .build();
    }

    private void dealStatusShouldPending(DealStatus purchaseStatus) {
        if (!DealStatus.PENDING.equals(purchaseStatus)) {
            throw new IllegalStateException("Not expected deal status.");
        }
    }
}
