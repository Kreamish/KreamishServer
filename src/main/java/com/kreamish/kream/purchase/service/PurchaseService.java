package com.kreamish.kream.purchase.service;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.purchase.dto.PendingPurchaseResponseDto;
import com.kreamish.kream.purchase.dto.SaleNowPriceDto;

public interface PurchaseService {

    SaleNowPriceDto getSaleNowPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo);

    PendingPurchaseResponseDto getPendingPurchasesByItemId(Long itemId);

    PendingPurchaseResponseDto getPendingPurchasesByItemSizesId(Long itemSizesId);
}
