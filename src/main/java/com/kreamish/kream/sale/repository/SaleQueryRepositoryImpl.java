package com.kreamish.kream.sale.repository;

import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.sale.entity.QSale.*;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.sale.entity.Sale;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaleQueryRepositoryImpl implements SaleQueryRepository{

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
}
