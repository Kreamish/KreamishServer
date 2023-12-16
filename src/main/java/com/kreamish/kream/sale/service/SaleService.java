package com.kreamish.kream.sale.service;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.BuyNowPriceDto;
import com.kreamish.kream.sale.dto.PendingSaleResponseDto;

public interface SaleService {

    BuyNowPriceDto getBuyNowPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo);

    PendingSaleResponseDto getPendingSalesByItemId(Long itemId);

    PendingSaleResponseDto getPendingSaleByItemSizesId(Long itemSizesId);
}
