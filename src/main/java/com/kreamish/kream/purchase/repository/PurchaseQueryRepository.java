package com.kreamish.kream.purchase.repository;

import com.kreamish.kream.purchase.dto.PendingPurchaseDto;
import com.kreamish.kream.purchase.dto.SaleNowPriceDto;
import java.util.List;

public interface PurchaseQueryRepository {

    SaleNowPriceDto findTheHighestPriceByItemSizesId(Long itemSizesId);

    SaleNowPriceDto findTheHighestPriceByItemId(Long itemId);

    List<PendingPurchaseDto> findPurchaseByDealStatusAndItemId(Long itemId);

    List<PendingPurchaseDto> findPurchaseByDealStatusAndItemSizesId(Long itemSizesId);
}
