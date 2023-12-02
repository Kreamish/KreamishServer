package com.kreamish.kream.sale.dto;

import com.kreamish.kream.common.entity.DealStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDetailResponseDto {
    private Long saleId;
    private Long itemSizesId;
    private Long memberId;
    private Long salePrice;
    private DealStatus status;
}
