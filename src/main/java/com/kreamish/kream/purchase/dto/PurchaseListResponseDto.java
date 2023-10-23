package com.kreamish.kream.purchase.dto;

import java.util.List;
import lombok.Data;

@Data
public class PurchaseListResponseDto {
    List<PurchaseDetailResponseDto> purchases;
}
