package com.kreamish.kream.purchase.service;

import com.kreamish.kream.purchase.dto.PurchaseDeleteResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseListResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterResponseDto;
import jakarta.annotation.Nullable;

public interface PurchaseService {

    PurchaseRegisterResponseDto createPurchaseAndProceedTrade(Long memberId, Long itemSizesId, Long price);

    PurchaseDeleteResponseDto withdrawPurchase(Long memberId, Long purchasesId);

    PurchaseListResponseDto findPurchasesByMemberId(Long memberId, @Nullable Boolean isCompleteStatus);

    PurchaseListResponseDto findPurchasesByItemSizesId(Long itemSizesId, @Nullable Boolean isComplete);
}
