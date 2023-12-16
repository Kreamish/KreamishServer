package com.kreamish.kream.price.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.price.dto.TotalPriceInfoResponseDto;
import com.kreamish.kream.price.facade.PriceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceFacade priceFacade;

    @Operation(
        summary = "상품 사이즈 별 최근 거래 가격, 즉시 구매, 판매 가격 정보 조회",
        description = "itemId와 itemSizesId로 최근 거래 가격, 즉시 구매, 판매 가격 조회. itemSizesId가 제공되지 않으면 모든 사이즈 별 가격 조회"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스"),
        @ApiResponse(responseCode = "400", description = "잘못된 인자 전달"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @GetMapping({"/items/{item-id}", "/items/{item-id}/item-sizes/{item-sizes-id}"})
    public ResponseEntity<ApiResult<TotalPriceInfoResponseDto>> getRecentTradedPrice(
        @PathVariable("item-id") Long itemId,
        @PathVariable(value = "item-sizes-id", required = false) Long itemSizesId,
        @Login(required = false) LoginMemberInfo loginMemberInfo
    ) {
        TotalPriceInfoResponseDto totalPriceInfoResponseDto = priceFacade.getTotalPriceInfo(itemId,
            itemSizesId, loginMemberInfo);

        return ResponseEntity.ok(success(totalPriceInfoResponseDto));
    }
}
