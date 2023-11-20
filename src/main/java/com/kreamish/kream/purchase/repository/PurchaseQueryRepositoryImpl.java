package com.kreamish.kream.purchase.repository;

import static com.kreamish.kream.itemsizes.entity.QItemSizes.*;
import static com.kreamish.kream.member.entity.QMember.*;
import static com.kreamish.kream.purchase.entity.QPurchase.*;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.purchase.entity.Purchase;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseQueryRepositoryImpl implements PurchaseQueryRepository {

    private final JPAQueryFactory query;

    public List<Purchase> findByMember(Member byMember, @Nullable Boolean isComplete) {
        return query.select(purchase)
            .from(purchase)
            .join(purchase.member, member).join(purchase.itemSizes, itemSizes)
            .fetchJoin()
            .where(member.eq(byMember), dealStatusCheck(purchase.purchaseStatus, isComplete))
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
}
