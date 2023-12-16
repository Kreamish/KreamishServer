package com.kreamish.kream.trade.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.item.dto.MarketPricesGraphResponseDto;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.trade.dto.TradeHistoryResponseDto;
import com.kreamish.kream.trade.enums.Period;
import com.kreamish.kream.trade.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;

    @Operation(
        summary = "시세 그래프를 위한 체결 거래 내역 반환",
        description = "itemId와 기간으로 거래 내역을 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스"),
        @ApiResponse(responseCode = "401", description = "미로그인 유저"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @GetMapping(value = "/items/{item-id}/chart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<MarketPricesGraphResponseDto>> getMarketPricesGraph(
        @Login LoginMemberInfo loginMemberInfo,
        @PathVariable("item-id") Long itemId,
        @RequestParam Period period
    ) {
        final MarketPricesGraphResponseDto marketPricesGraph = tradeService.getMarketPricesGraph(
            itemId, period);

        return new ResponseEntity<>(success(marketPricesGraph), HttpStatus.OK);
    }

    @Operation(
        summary = "거래된 내역 리스트 반환",
        description = "특정 아이템에 대한 거래 내역 전체 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스"),
        @ApiResponse(responseCode = "401", description = "미로그인 유저"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @GetMapping(value = "/items/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<TradeHistoryResponseDto>> getTradeHistoryByItemId(
        @Login LoginMemberInfo loginMemberInfo,
        @PathVariable("item-id") Long itemId
    ) {
        TradeHistoryResponseDto tradeHistoryResponseDto = tradeService.getTradeHistoryByItemId(
            itemId);

        return new ResponseEntity<>(success(tradeHistoryResponseDto), HttpStatus.OK);
    }

    @Operation(
        summary = "거래된 내역 리스트 반환",
        description = "특정 아이템 사이즈에 대한 거래 내역 전체 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스"),
        @ApiResponse(responseCode = "401", description = "미로그인 유저"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @GetMapping(value = "/items/{item-id}/item-sizes/{item-sizes-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<TradeHistoryResponseDto>> getTradeHistoryByItemSizesId(
        @Login LoginMemberInfo loginMemberInfo,
        @PathVariable("item-id") Long itemId,
        @PathVariable("item-sizes-id") Long itemSizesId
    ) {
        TradeHistoryResponseDto tradeHistoryResponseDto = tradeService.getTradeHistoryByItemSizesId(
            itemId, itemSizesId);

        return new ResponseEntity<>(success(tradeHistoryResponseDto), HttpStatus.OK);
    }

}
