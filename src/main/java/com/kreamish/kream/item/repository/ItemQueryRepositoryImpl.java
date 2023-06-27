package com.kreamish.kream.item.repository;


import static com.kreamish.kream.item.entity.QItem.item;
import static com.kreamish.kream.item.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.legacy.entity.QCollection.collection;
import static com.kreamish.kream.legacy.entity.QItemCollectionRel.itemCollectionRel;
import static com.kreamish.kream.legacy.entity.QSale.sale;

import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.legacy.entity.DealStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory query;

    public List<Item> findItemsWhereLikes(String name) {
        return query.select(item)
            .from(item)
            .where(item.name.like(name))
            .fetch();
    }

    /**
     * condition 값 중, 유효하지 않는 ID를 갖는 경우 체크 필요.
     */
    @Override
    public Page<Item> findItemsByCondition(ItemListSearchCondition condition, Pageable pageable) {

        boolean hasPriceCondition = condition.getMinPrice() != null && condition.getMaxPrice() != null;

        List<Long> findItemIdByCollectionIds = query.select(itemCollectionRel.item.itemId)
            .from(itemCollectionRel)
            .join(itemCollectionRel.collection, collection)
            .where(inOrElseGetTrue(
                collection.collectionId,
                condition.getCollectionIds()
            ))
            .fetch();

        List<Long> findItemSizesIdByPriceCondition = null;
        if (hasPriceCondition) {
            findItemSizesIdByPriceCondition = query.select(sale.itemSizes.itemSizesId)
                .from(sale)
                .where(sale.saleStatus.eq(DealStatus.PENDING))
                .groupBy(sale.itemSizes)
                .having(sale.salePrice.min().between(
                    condition.getMinPrice(),
                    condition.getMaxPrice()
                ))
                .fetch();
        }

        List<Long> findItemIdBySizesCondition = query.select(itemSizes.item.itemId)
            .from(itemSizes)
            .where(inOrElseGetTrue(itemSizes.size, condition.getSizes()))
            .fetch();

        List<Item> queryResult = query.select(item)
            .from(item)
            .where(
                inOrElseGetTrue(item.category.categoryId, condition.getCategoryIds()),
                inOrElseGetTrue(item.brand.brandId, condition.getBrandIds()),
                inOrElseGetTrue(item.itemId, findItemIdByCollectionIds),
                hasPriceCondition ?
                    item.itemId.in(
                        JPAExpressions
                            .select(itemSizes.item.itemId)
                            .from(itemSizes)// Item - ItemSizes - Sale
                            .where(inOrElseGetTrue(itemSizes.itemSizesId, findItemSizesIdByPriceCondition))
                    ) : null,
                inOrElseGetTrue(item.itemId, findItemIdBySizesCondition)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long count = query.select(item.count())
            .from(item)
            .where(
                inOrElseGetTrue(item.category.categoryId, condition.getCategoryIds()),
                inOrElseGetTrue(item.brand.brandId, condition.getBrandIds()),
                inOrElseGetTrue(item.itemId, findItemIdByCollectionIds),
                hasPriceCondition ?
                    item.itemId.in(
                        JPAExpressions
                            .select(itemSizes.item.itemId)
                            .from(itemSizes)// Item - ItemSizes - Sale
                            .where(inOrElseGetTrue(itemSizes.itemSizesId, findItemSizesIdByPriceCondition))
                    ) : null,
                inOrElseGetTrue(item.itemId, findItemIdBySizesCondition)
            )
            .fetchOne();

        if (count == null) {
            count = 0L;
        }
        return new PageImpl<>(queryResult, pageable, count);
    }

    private BooleanExpression inOrElseGetTrue(StringPath path, List<String> condition) {
        if (condition == null || condition.isEmpty()) {
            return null;
        }
        return path.in(condition);
    }

    private BooleanExpression inOrElseGetTrue(NumberPath<Long> path, List<Long> condition) {
        if (condition == null || condition.isEmpty()) {
            return null;
        }
        return path.in(condition);
    }
}
