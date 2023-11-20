package com.kreamish.kream.sale.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.sale.entity.Sale;
import java.util.Optional;

public interface SaleQueryRepository {
    Optional<Sale> findMinPriceSaleByItemSizesId(ItemSizes itemSizes);
}
