package com.kreamish.kream.like.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import java.util.List;

public interface LikeQueryRepository {

    public Long getLikeCnt(Long itemId);

    List<ItemSizes> getLikedItemSizes(Long itemId, Long memberId);
}
