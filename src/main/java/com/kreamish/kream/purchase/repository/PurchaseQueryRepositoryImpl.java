package com.kreamish.kream.purchase.repository;

import static com.kreamish.kream.common.entity.DealStatus.PENDING;
import static com.kreamish.kream.item.entity.QItem.item;
import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.purchase.entity.QPurchase.purchase;

import com.kreamish.kream.purchase.dto.PendingPurchaseDto;
import com.kreamish.kream.purchase.dto.SaleNowPriceDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseQueryRepositoryImpl implements PurchaseQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<PendingPurchaseDto> findPurchaseByDealStatusAndItemId(Long itemId) {
        return query
            .select(
                Projections.bean(
                    PendingPurchaseDto.class,
                    itemSizes.size.as("itemSizes"),
                    purchase.purchasePrice.as("price"),
                    purchase.purchasePrice.count().as("quantity")
                )
            )
            .from(purchase)
            .join(purchase.itemSizes, itemSizes)
            .join(itemSizes.item, item)
            .where(
                purchase.purchaseStatus.eq(PENDING),
                item.itemId.eq(itemId))
            .groupBy(purchase.itemSizes, purchase.purchasePrice)
            .fetch();
    }

    @Override
    public List<PendingPurchaseDto> findPurchaseByDealStatusAndItemSizesId(Long itemSizesId) {
        return query
            .select(
                Projections.bean(
                    PendingPurchaseDto.class,
                    purchase.itemSizes.size.as("itemSizes"),
                    purchase.purchasePrice.as("price"),
                    purchase.purchasePrice.count().as("quantity")
                )
            )
            .from(purchase)
            .where(
                purchase.purchaseStatus.eq(PENDING),
                purchase.itemSizes.itemSizesId.eq(itemSizesId))
            .groupBy(purchase.itemSizes, purchase.purchasePrice)
            .fetch();
    }

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
