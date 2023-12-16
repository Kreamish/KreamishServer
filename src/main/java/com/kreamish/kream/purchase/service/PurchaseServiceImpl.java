package com.kreamish.kream.purchase.service;

import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.purchase.dto.PendingPurchaseDto;
import com.kreamish.kream.purchase.dto.PendingPurchaseResponseDto;
import com.kreamish.kream.purchase.dto.SaleNowPriceDto;
import com.kreamish.kream.purchase.repository.PurchaseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public PendingPurchaseResponseDto getPendingPurchasesByItemSizesId(Long itemSizesId) {
        final List<PendingPurchaseDto> pendingPurchases = purchaseRepository.findPurchaseByDealStatusAndItemSizesId(
            itemSizesId);

        return PendingPurchaseResponseDto.of(pendingPurchases);
    }

    @Override
    public PendingPurchaseResponseDto getPendingPurchasesByItemId(Long itemId) {
        final List<PendingPurchaseDto> pendingPurchases = purchaseRepository.findPurchaseByDealStatusAndItemId(
            itemId);

        return PendingPurchaseResponseDto.of(pendingPurchases);
    }

    @Override
    public SaleNowPriceDto getSaleNowPrice(Long itemId, Long itemSizesId,
        LoginMemberInfo loginMemberInfo) {
        // 미로그인 유저 or 모든 사이즈
        if (loginMemberInfo.isNotLoggedIn() || (!loginMemberInfo.isNotLoggedIn()
            && itemSizesId == null)) {
            return purchaseRepository.findTheHighestPriceByItemId(itemId);
        }
        // 로그인 유저 and 특정 아이템 사이즈
        return purchaseRepository.findTheHighestPriceByItemSizesId(itemSizesId);
    }
}
