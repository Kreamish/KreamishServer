package com.kreamish.kream.favorite.repository;

import static com.kreamish.kream.favorite.enity.QFavorite.favorite;
import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.querydsl.jpa.JPAExpressions.select;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FavoriteQueryRepositoryImpl implements FavoriteQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Long getFavoriteCnt(Long itemId) {
        return query.select(favorite.count())
            .from(favorite)
            .where(favorite.itemSizes.itemSizesId.in(
                select(itemSizes.itemSizesId)
                    .from(itemSizes)
                    .where(itemSizes.item.itemId.eq(itemId))
            )).fetchOne();
    }
}
