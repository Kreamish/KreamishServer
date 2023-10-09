package com.kreamish.kream.purchase.repository;

import com.kreamish.kream.purchase.dto.SaleNowPriceDto;

public interface PurchaseQueryRepository {

    SaleNowPriceDto findTheHighestPriceByItemSizesId(Long itemSizesId);

    SaleNowPriceDto findTheHighestPriceByItemId(Long itemId);
}
