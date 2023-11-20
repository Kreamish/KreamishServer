package com.kreamish.kream.purchase.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kreamish.kream.trade.dto.TradeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseRegisterResponseDto {
    private Long purchaseId;

    @JsonInclude(Include.NON_NULL)
    private TradeResponseDto trade;

    public PurchaseRegisterResponseDto(Long purchaseId) {
        this.purchaseId = purchaseId;
        this.trade = null;
    }
}
