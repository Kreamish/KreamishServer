package com.kreamish.kream.price.facade;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.price.dto.TotalPriceInfoResponseDto;

public interface PriceFacade {

    TotalPriceInfoResponseDto getTotalPriceInfo(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo);
}
