package com.kreamish.kream.purchase.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseListResponseDto {
    List<PurchaseDetailResponseDto> purchases;
}
