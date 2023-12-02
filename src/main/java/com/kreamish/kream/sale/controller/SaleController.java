package com.kreamish.kream.sale.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.SaleDeleteResponseDto;
import com.kreamish.kream.sale.dto.SaleListResponseDto;
import com.kreamish.kream.sale.dto.SaleRegisterRequestDto;
import com.kreamish.kream.sale.dto.SaleRegisterResponseDto;
import com.kreamish.kream.sale.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ApiResult<SaleRegisterResponseDto>> createSales(
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "본인 등록 판매 입찰 건 조회",
        description = "본인이 등록한 판매 입찰 목록 조회.",
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @Parameter(
        name = "isComplete",
        description = "거래 완료 여부.",
        examples = {
            @ExampleObject(name = "null", description = "모두 조회"),
            @ExampleObject(name = "true", value = "true", description = "거래 완료된 판매 입찰 목록 조회"),
            @ExampleObject(name = "false", value = "false", description = "거래 완료되지 않은 판매 목록 조회"),
        }
    )
    public ResponseEntity<ApiResult<SaleListResponseDto>> getSales(
        @RequestParam(required = false) Boolean isComplete,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        Optional<SaleListResponseDto> optionalResponse = Optional.ofNullable(
            saleService.findSalesByMemberId(
                loginMemberInfo.getMemberId(),
                isComplete
            )
        );

        SaleListResponseDto response = null;

        if (optionalResponse.isPresent()) {
            response = optionalResponse.get();
        }

        return new ResponseEntity<>(success(response), HttpStatus.OK);
    }

    @GetMapping("/item-sizes-id/{item-sizes-id}")
    @Operation(
        summary = "상품 사이즈에 대해 등록된 구매 입찰 건 조회",
        description = "item-sizes 로 등록된 구매 입찰 건 조회"
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @Parameter(
        name = "isComplete",
        description = "거래 완료 여부.",
        examples = {
            @ExampleObject(name = "null", description = "모두 조회"),
            @ExampleObject(name = "true", value = "true", description = "거래 완료된 구매 입찰 목록 조회"),
            @ExampleObject(name = "false", value = "false", description = "거래 완료되지 않은 구매 목록 조회"),
        }
    )
    public ResponseEntity<ApiResult<SaleListResponseDto>> getPurchasesByItemSizes(
        @RequestParam(required = false) Boolean isComplete,
        @PathVariable("item-sizes-id") Long itemSizesId
    ) {
        SaleListResponseDto response = saleService.findSalesByItemSizesId(itemSizesId, isComplete);

        return new ResponseEntity<>(success(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{sale-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "판매 입찰 취소",
        description = "판매 입찰 건 취소",
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @ApiResponse(responseCode = "200", description = "구매 입찰 취소(삭제) 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 구매 입찰에 대한 요청")
    public ResponseEntity<ApiResult<SaleDeleteResponseDto>> withdrawSale(
        @PathVariable("sale-id") Long saleId,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        SaleDeleteResponseDto response = saleService.withdrawSale(loginMemberInfo.getMemberId(), saleId);

        return new ResponseEntity<>(success(response), HttpStatus.OK);
    }
}
