package com.kreamish.kream.sale.repository;

import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.member.entity.QMember.member;
import static com.kreamish.kream.purchase.entity.QPurchase.purchase;
import static com.kreamish.kream.sale.entity.QSale.*;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.sale.entity.Sale;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaleQueryRepositoryImpl implements SaleQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Sale> findMinPriceSaleByItemSizesId(ItemSizes itemSizesParam) {
        return Optional.ofNullable(
            query.select(sale)
                .from(sale)
                .join(sale.itemSizes, itemSizes)
                .where(itemSizes.isNotNull(), itemSizes.eq(itemSizesParam), sale.saleStatus.eq(DealStatus.PENDING))
                .orderBy(sale.salePrice.asc())
                .limit(1)
                .fetchOne()
        );
    }

    @Override
    public List<Sale> findByMember(Member byMember, Boolean isComplete) {
        return query.select(sale)
            .from(sale)
            .join(sale.member, member).join(sale.itemSizes, itemSizes)
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
