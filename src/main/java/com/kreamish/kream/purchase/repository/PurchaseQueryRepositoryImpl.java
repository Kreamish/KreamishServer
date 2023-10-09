package com.kreamish.kream.purchase.repository;

import static com.kreamish.kream.common.entity.DealStatus.PENDING;
import static com.kreamish.kream.purchase.entity.QPurchase.purchase;

import com.kreamish.kream.purchase.dto.SaleNowPriceDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseQueryRepositoryImpl implements PurchaseQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public SaleNowPriceDto findTheHighestPriceByItemSizesId(Long itemSizesId) {
        return findTheHighestPurchasePrice(purchase.itemSizes.itemSizesId, itemSizesId)
            .orElse(SaleNowPriceDto.empty());
    }

    @Override
    public SaleNowPriceDto findTheHighestPriceByItemId(Long itemId) {
        return findTheHighestPurchasePrice(purchase.itemSizes.item.itemId, itemId)
            .orElse(SaleNowPriceDto.empty());
    }

    private Optional<SaleNowPriceDto> findTheHighestPurchasePrice(NumberPath<Long> itemOrItemSizes,
        Long id) {
        return Optional.ofNullable(query
            .select(Projections.bean(SaleNowPriceDto.class, purchase.purchaseId,
                purchase.purchasePrice))
            .from(purchase)
            .where(itemOrItemSizes.eq(id), purchase.purchaseStatus.eq(PENDING))
            .orderBy(purchase.purchasePrice.desc(), purchase.purchaseId.desc())
            .fetchOne());
    }
}
