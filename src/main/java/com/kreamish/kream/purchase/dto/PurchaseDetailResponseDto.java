package com.kreamish.kream.purchase.dto;

import com.kreamish.kream.common.entity.DealStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDetailResponseDto {
    private Long purchaseId;
    private Long itemSizesId;
    private Long memberId;
    private Long purchasePrice;
    private DealStatus status;
}
