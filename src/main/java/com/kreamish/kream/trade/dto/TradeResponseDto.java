package com.kreamish.kream.trade.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeResponseDto {
    private Long tradeId;
    private Long purchaseId;
    private Long saleId;
    private Long tradePrice;
    private LocalDateTime tradeDate;
}
