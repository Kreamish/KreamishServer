package com.kreamish.kream.trade.repository;

import static com.kreamish.kream.item.entity.QItem.item;
import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.purchase.entity.QPurchase.purchase;
import static com.kreamish.kream.sale.entity.QSale.sale;
import static com.kreamish.kream.trade.entity.QTrade.trade;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.trade.dto.TradeOrderDto;
import com.kreamish.kream.trade.enums.Period;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TradeQueryRepositoryImpl implements TradeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<TradeOrderDto> findAllByItemSizesId(Long itemSizesId) {
        return query.select(
                Projections.bean(
                    TradeOrderDto.class,
                    itemSizes.itemSizesId.as("itemSizesId"),
                    itemSizes.size.as("itemSizes"),
                    trade.tradePrice.as("price"),
                    trade.createdAt.as("tradedAt")
                )
            )
            .from(trade)
            .join(trade.sale, sale)
            .join(sale.itemSizes, itemSizes)
            .where(itemSizes.itemSizesId.eq(itemSizesId))
            .fetch();
    }

    @Override
    public List<TradeOrderDto> findAllByItemIdAndCreatedAtForTheLastNMonths(Long itemId,
        Period period) {
        return jpaQueryTradeJoinedItemSizesAndSaleAndItem()
            .where(
                item.itemId.eq(itemId),
                getBetweenLastNMonthsAndNowOrElseGetNull(period)
            ).fetch();
    }

    @Override
    public List<TradeOrderDto> findAllByItemId(Long itemId) {
        return jpaQueryTradeJoinedItemSizesAndSaleAndItem()
            .where(
                item.itemId.eq(itemId)
            ).fetch();
    }

    @Override
    public RecentTradePriceInfoResponseDto findTheLastTwoTradePricesByItemSizesId(
        Long itemSizesId) {
        final BooleanExpression byItemSizesId = purchase.itemSizes.itemSizesId.eq(itemSizesId);

        return findTheLastTwoTradePrices(byItemSizesId);
    }

    @Override
    public RecentTradePriceInfoResponseDto findTheLastTwoTradePricesByItemId(Long itemId) {
        final BooleanExpression byItemId = purchase.itemSizes.item.itemId.eq(itemId);

        return findTheLastTwoTradePrices(byItemId);
    }

    private RecentTradePriceInfoResponseDto findTheLastTwoTradePrices(BooleanExpression byId) {
        final List<Long> purchases = findCompletedPurchases(byId);
        final List<Long> lastTwoTradePrices = query.select(trade.tradePrice)
            .from(trade)
            .where(trade.purchase.purchaseId.in(purchases))
            .orderBy(trade.createdAt.desc())
            .limit(2)
            .fetch();

        return RecentTradePriceInfoResponseDto.of(lastTwoTradePrices);
    }

    private List<Long> findCompletedPurchases(BooleanExpression booleanExpression) {
        return query.select(purchase.purchaseId)
            .from(purchase)
            .where(booleanExpression.and(purchase.purchaseStatus.eq(DealStatus.COMPLETE)))
            .fetch();
    }

    private JPAQuery<TradeOrderDto> jpaQueryTradeJoinedItemSizesAndSaleAndItem() {
        return query.select(
                Projections.bean(
                    TradeOrderDto.class,
                    itemSizes.itemSizesId.as("itemSizesId"),
                    itemSizes.size.as("itemSizes"),
                    trade.tradePrice.as("price"),
                    trade.createdAt.as("tradedAt")
                )
            )
            .from(trade)
            .join(trade.sale, sale)
            .join(sale.itemSizes, itemSizes)
            .join(itemSizes.item, item);
    }

    private BooleanExpression getBetweenLastNMonthsAndNowOrElseGetNull(Period period) {
        if (!period.equals(Period.ALL)) {
            final int months = Integer.parseInt(period.getValue());

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime LastNMonths = now.minusMonths(months);

            return trade.createdAt.between(LastNMonths, now);
        }

        return null;
    }
}
