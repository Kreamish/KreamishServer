package com.kreamish.kream.trade.entity.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.item.dto.LastTradedPricePerItemSizeDto;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/trade/")
public class TradeController {

    @Operation(
        summary = "상품 사이즈 별 최근 거래 가격 정보 조회",
        description = "itemId와 itemSizesId로 최근 거래 가격 조회. itemSizesId가 제공되지 않으면 모든 사이즈 별 가격 조회"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스"),
        @ApiResponse(responseCode = "400", description = "잘못된 인자 전달"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @GetMapping({"/price/{item-id}", "/price//{item-id}/itemsizes/{item-sizes-id}"})
    public ResponseEntity<ApiResult<List<LastTradedPricePerItemSizeDto>>> getLastTradedPrice(
        @PathVariable("item-id") Long itemId,
        @PathVariable("item-sizes-id") Long itemSizesId,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        List<LastTradedPricePerItemSizeDto> recentPriceInfo = itemService.getLastTradedPrice(itemId,
            itemSizesId, loginMemberInfo);

        return ResponseEntity.ok(success(recentPriceInfo));
    }

}
