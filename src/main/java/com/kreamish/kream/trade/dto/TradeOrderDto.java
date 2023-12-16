package com.kreamish.kream.trade.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonNaming
public class TradeOrderDto {

    private Long itemSizesId;
    private String itemSizes;
    private Long price;
    private LocalDateTime tradedAt;
}
