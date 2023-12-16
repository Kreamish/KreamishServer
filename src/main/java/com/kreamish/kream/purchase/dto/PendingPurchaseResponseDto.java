package com.kreamish.kream.purchase.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PendingPurchaseResponseDto {

    private List<PendingPurchaseDto> pendingPurchaseDtoList;

    public static PendingPurchaseResponseDto of(List<PendingPurchaseDto> pendingPurchaseDtoList) {
        return new PendingPurchaseResponseDto(pendingPurchaseDtoList);
    }
}
