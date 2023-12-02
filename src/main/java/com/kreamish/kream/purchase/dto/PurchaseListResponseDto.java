package com.kreamish.kream.purchase.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseListResponseDto {
    List<PurchaseDetailResponseDto> purchases;
}
