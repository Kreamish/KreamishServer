package com.kreamish.kream.sale.service;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.BuyNowPriceDto;

public interface SaleService {

    BuyNowPriceDto getBuyNowPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo);
}
