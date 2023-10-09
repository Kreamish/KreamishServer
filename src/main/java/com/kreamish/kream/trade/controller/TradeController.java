package com.kreamish.kream.trade.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.item.dto.MarketPricesGraphResponseDto;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
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
@RequestMapping("/trade")
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
    @GetMapping(value = "/item/{item-id}/chart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<MarketPricesGraphResponseDto>> getMarketPricesGraph(
        @Login LoginMemberInfo loginMemberInfo,
        @PathVariable Long itemId,
        @RequestParam(required = false, defaultValue = "all") Period period
    ) {

        /*
        1. 거래내역 조회
            /trade/item/1/chart?period=전체,1,3,6,12
        2. 아이템사이즈 별 페이징, 체결 거래(거래가격, 거래 사이즈,  날짜),
           판매 입찰, 구매 입찰(거래 사이즈, 가격, 해당 가격대에 올라온 상품 수량)
           /trade/item/1?itemsizes=1&paging
            ,
            todo : 거래내역 전체, 거래 내역 페이징, 거래내역 아이템 아이디로, 등등을 api 여러 개로 하지말고 여기 인자로 받아서 service에서 다른걸 호출하게
            todo : 구매, 판매 입찰 페이징
         */

        final MarketPricesGraphResponseDto marketPricesGraph = tradeService.getMarketPricesGraph(
            itemId, period);

        return new ResponseEntity<>(success(marketPricesGraph), HttpStatus.OK);
    }

}
