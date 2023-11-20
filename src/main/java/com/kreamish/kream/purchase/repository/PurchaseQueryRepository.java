package com.kreamish.kream.purchase.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.purchase.entity.Purchase;
import jakarta.annotation.Nullable;
import java.util.List;

public interface PurchaseQueryRepository {
    List<Purchase> findByMember(Member byMember, @Nullable Boolean isComplete);

    List<Purchase> findByItemSizes(ItemSizes itemSizes, @Nullable Boolean isComplete);
}
