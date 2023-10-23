package com.kreamish.kream.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeleteResponseDto {
    private Long purchaseId;
    private Long memberId;
    private Long itemSizesId;
    private Long beforePrice;
}
