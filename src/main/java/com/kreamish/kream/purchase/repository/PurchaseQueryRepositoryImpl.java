package com.kreamish.kream.purchase.repository;

import static com.kreamish.kream.itemsizes.entity.QItemSizes.*;
import static com.kreamish.kream.member.entity.QMember.*;
import static com.kreamish.kream.purchase.entity.QPurchase.*;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.purchase.entity.Purchase;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseQueryRepositoryImpl implements PurchaseQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Purchase> findByMember(Member byMember, @Nullable Boolean isComplete) {
        return query.select(purchase)
            .from(purchase)
            .join(purchase.member, member).join(purchase.itemSizes, itemSizes)
            .where(member.eq(byMember), dealStatusCheck(purchase.purchaseStatus, isComplete))
            .fetch();
    }

    @Override
    public List<Purchase> findByItemSizes(ItemSizes byItemSizes, @Nullable Boolean isComplete) {
        return query.select(purchase)
            .from(purchase)
            .join(purchase.itemSizes, itemSizes).join(purchase.member, member)
            .where(itemSizes.eq(byItemSizes), dealStatusCheck(purchase.purchaseStatus, isComplete))
            .fetch();
    }

    private Predicate dealStatusCheck(EnumPath<DealStatus> purchaseStatus, @Nullable Boolean isComplete) {
        if (isComplete == null) {
            return null;
        }

        if (isComplete) {
            return purchaseStatus.eq(DealStatus.COMPLETE);
        } else {
            return purchaseStatus.ne(DealStatus.COMPLETE);
        }
    }

    @Override
    public Optional<Purchase> findMaxPricePurchaseByItemSizesId(ItemSizes itemSizesParam) {
        return Optional.ofNullable(
            query.select(purchase)
                .from(purchase)
                .join(purchase.itemSizes, itemSizes)
                .where(itemSizes.isNotNull(),
                    itemSizes.eq(itemSizesParam),
                    purchase.purchaseStatus.eq(DealStatus.PENDING)
                )
                .orderBy(purchase.purchasePrice.desc())
                .limit(1)
                .fetchOne()
        );
    }
}
