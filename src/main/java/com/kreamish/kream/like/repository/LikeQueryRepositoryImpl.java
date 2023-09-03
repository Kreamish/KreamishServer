package com.kreamish.kream.like.repository;

import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.like.entity.QLike.like;
import static com.querydsl.jpa.JPAExpressions.select;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeQueryRepositoryImpl implements LikeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Long getLikeCnt(Long itemId) {
        return query.select(like.count())
            .from(like)
            .where(like.itemSizes.itemSizesId.in(
                select(itemSizes.itemSizesId)
                    .from(itemSizes)
                    .where(itemSizes.item.itemId.eq(itemId))
            )).fetchOne();
    }

    @Override
    public List<ItemSizes> getLikedItemSizes(Long itemId, Long memberId) {
        /*
        select itemSizes
        from (select itemSizes
        from like
        where like.member = memberId) as a
        where a.itemSizes.item = itemId
        */
        List<Long> itemSizesIdListMemberLike = query.select(like.itemSizes.itemSizesId)
            .from(like)
            .where(like.member.memberId.eq(memberId))
            .fetch();

        List<ItemSizes> likedItemSizes = query.select(like.itemSizes)
            .from(like)
            .where(like.itemSizes.item.itemId.in(itemSizesIdListMemberLike))
            .fetch();

        return likedItemSizes;
    }
}
