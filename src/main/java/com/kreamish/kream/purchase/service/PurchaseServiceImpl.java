package com.kreamish.kream.purchase.service;

import com.kreamish.kream.purchase.dto.PurchaseDeleteResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseListResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterResponseDto;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Override
    public PurchaseRegisterResponseDto createPurchaseAndProceedTrade(Long memberId, Long itemSizesId, Long price) {
        return null;
    }

    @Override
    public PurchaseDeleteResponseDto withdrawPurchase(Long memberId, Long purchasesId) {
        return null;
    }

    @Override
    public PurchaseListResponseDto findPurchasesByMemberId(Long memberId, @Nullable Boolean isCompleteStatus) {
        return null;
    }

    @Override
    public PurchaseListResponseDto findPurchasesByItemSizesId(Long memberId, @Nullable Boolean isCompleteStatus) {
        return null;
    }
}
