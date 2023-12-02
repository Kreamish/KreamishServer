package com.kreamish.kream.sale.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.sale.entity.Sale;
import java.util.List;
import java.util.Optional;

public interface SaleQueryRepository {

    Optional<Sale> findMinPriceSaleByItemSizesId(ItemSizes itemSizes);

    List<Sale> findByMember(Member member, Boolean isComplete);

    List<Sale> findByItemSizes(ItemSizes itemSizes, Boolean isComplete);
}
