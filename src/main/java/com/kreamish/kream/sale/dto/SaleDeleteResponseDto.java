package com.kreamish.kream.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDeleteResponseDto {

    private Long purchaseId;
    private Long memberId;
    private Long itemSizesId;
    private Long beforePrice;
}
