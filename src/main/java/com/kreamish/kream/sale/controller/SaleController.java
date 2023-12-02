package com.kreamish.kream.sale.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.SaleRegisterRequestDto;
import com.kreamish.kream.sale.dto.SaleRegisterResponseDto;
import com.kreamish.kream.sale.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @PostMapping(value = "/item-sizes/{item-sizes-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "판매 입찰 등록",
        description = "특정 상품 판매 입찰 등록",
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @ApiResponse(responseCode = "201", description = "등록 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    public ResponseEntity<ApiResult<SaleRegisterResponseDto>> createPurchase(
        @RequestBody SaleRegisterRequestDto requestDto,
        @PathVariable("item-sizes-id") Long itemSizesId,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        SaleRegisterResponseDto response = saleService.createSaleAndProceedTrade(
            loginMemberInfo.getMemberId(),
            itemSizesId,
            requestDto.getPrice()
        );
        return new ResponseEntity<>(success(response), HttpStatus.CREATED);
    }
}
