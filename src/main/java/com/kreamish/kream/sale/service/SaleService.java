package com.kreamish.kream.sale.service;

import com.kreamish.kream.sale.dto.SaleDeleteResponseDto;
import com.kreamish.kream.sale.dto.SaleListResponseDto;
import com.kreamish.kream.sale.dto.SaleRegisterResponseDto;

public interface SaleService {

    SaleRegisterResponseDto createSaleAndProceedTrade(Long memberId, Long itemSizesId, Long price);

    SaleListResponseDto findSalesByMemberId(Long memberId, Boolean isComplete);

    SaleListResponseDto findSalesByItemSizesId(Long itemSizesId, Boolean isComplete);

    SaleDeleteResponseDto withdrawSale(Long memberId, Long saleId);
}
