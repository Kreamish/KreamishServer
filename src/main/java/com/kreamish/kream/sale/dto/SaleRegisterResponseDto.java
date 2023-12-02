package com.kreamish.kream.sale.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kreamish.kream.trade.dto.TradeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleRegisterResponseDto {

    private Long saleId;

    @JsonInclude(Include.NON_NULL)
    private TradeResponseDto trade;

    public SaleRegisterResponseDto(Long saleId) {
        this.saleId = saleId;
        this.trade = null;
    }
}
