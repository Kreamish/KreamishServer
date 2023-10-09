package com.kreamish.kream.sale.repository;

import com.kreamish.kream.sale.entity.Sale;
import java.util.Optional;

public interface SaleQueryRepository {

    Optional<Sale> findTheLowestPriceByItemId(Long itemId);

    Optional<Sale> findTheLowestPriceByItemSizesId(Long itemSizesId);
}
