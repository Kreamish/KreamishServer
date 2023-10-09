package com.kreamish.kream.trade.repository;

import static com.kreamish.kream.item.entity.QItem.item;
import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.purchase.entity.QPurchase.purchase;
import static com.kreamish.kream.sale.entity.QSale.sale;
import static com.kreamish.kream.trade.entity.QTrade.trade;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.item.dto.RecentTradePriceInfoResponseDto;
import com.kreamish.kream.trade.entity.Trade;
import com.kreamish.kream.trade.enums.Period;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TradeQueryRepositoryImpl implements TradeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Trade> findAllByItemIdAndCreatedAtMoreOrEqualThan(long itemId, Period period) {
        return query.select(trade)
            .from(trade)
            .join(trade.sale, sale)
            .join(trade.sale.itemSizes, itemSizes)
            .join(trade.sale.itemSizes.item, item)
            .where(
                item.itemId.eq(itemId)
            ).fetch();
    }

/*
        select t
        from trade
        join t.sale s on t.sale_id = s.id
        join s.itemsizes is on t.s.is.id = is.id
        where 1=1
        is.item_id = :itemId and
        :period <= createdAt
         */

    private Predicate periodCondition(String period) {
        if (period == "all") {
            return null;
        }
        try {
            int periodInt = Integer.parseInt(period);
        } catch (NumberFormatException e) {

        }
        return null;
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
}
