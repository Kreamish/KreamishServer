package com.kreamish.kream.purchase.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.purchase.dto.PurchaseDeleteResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseDetailResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseListResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterRequestDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterResponseDto;
import com.kreamish.kream.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
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
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping(value = "/item-sizes/{item-sizes-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "구매 입찰 등록",
        description = "특정 상품 구매 입찰 등록",
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
    })
    public ResponseEntity<ApiResult<PurchaseRegisterResponseDto>> createPurchase(
        @RequestBody PurchaseRegisterRequestDto requestDto,
        @PathVariable("item-sizes-id") Long itemSizesId,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        PurchaseRegisterResponseDto response = purchaseService.createPurchaseAndProceedTrade(
            loginMemberInfo.getMemberId(),
            itemSizesId,
            requestDto.getPrice()
        );
        return new ResponseEntity<>(success(response), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "본인 등록 구매 입찰 건 조회",
        description = "본인이 등록한 구매 입찰 목록 조회.",
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "204", description = "성공이지만 데이터는 없는 경우"),
    })
    @Parameters({
        @Parameter(
            name = "isComplete",
            description = "거래 완료 여부.",
            examples = {
                @ExampleObject(name = "null", description = "모두 조회"),
                @ExampleObject(name = "true", value = "true", description = "거래 완료된 구매 입찰 목록 조회"),
                @ExampleObject(name = "false", value = "false", description = "거래 완료되지 않은 구매 목록 조회"),
            }
        )
    })
    public ResponseEntity<ApiResult<PurchaseListResponseDto>> getPurchases(
        @RequestParam(required = false) Boolean isComplete,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        PurchaseListResponseDto response = purchaseService.findPurchasesByMemberId(
            loginMemberInfo.getMemberId(),
            isComplete
        );

        List<PurchaseDetailResponseDto> purchases = response.getPurchases();

        if (purchases == null || purchases.isEmpty()) {
            return new ResponseEntity<>(success(response), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(success(response), HttpStatus.OK);
        }
    }

    @GetMapping("/item-sizes-id/{item-sizes-id}")
    @Operation(
        summary = "상품 사이즈에 대해 등록된 구매 입찰 건 조회",
        description = "item-sizes 로 등록된 구매 입찰 건 조회"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "204", description = "성공이지만 데이터는 없는 경우")
    })
    @Parameters({
        @Parameter(
            name = "isComplete",
            description = "거래 완료 여부.",
            examples = {
                @ExampleObject(name = "null", description = "모두 조회"),
                @ExampleObject(name = "true", value = "true", description = "거래 완료된 구매 입찰 목록 조회"),
                @ExampleObject(name = "false", value = "false", description = "거래 완료되지 않은 구매 목록 조회"),
            }
        )
    })
    public ResponseEntity<ApiResult<PurchaseListResponseDto>> getPurchasesByItemSizes(
        @RequestParam(required = false) Boolean isComplete,
        @PathVariable("item-sizes-id") Long itemSizesId
    ) {
        PurchaseListResponseDto response = purchaseService.findPurchasesByItemSizesId(itemSizesId, isComplete);

        List<PurchaseDetailResponseDto> purchases = response.getPurchases();

        if (purchases == null || purchases.isEmpty()) {
            return new ResponseEntity<>(success(response), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(success(response), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{purchases-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "구매 입찰 취소",
        description = "구매 입찰 건 취소",
        security = {
            @SecurityRequirement(name = "basicAuth")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "등록 성공"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 구매 입찰에 대한 요청"),
    })
    public ResponseEntity<ApiResult<PurchaseDeleteResponseDto>> withdrawPurchase(
        @PathVariable("purchases-id") Long purchasesId,
        @Login LoginMemberInfo loginMemberInfo
    ) {
        PurchaseDeleteResponseDto response = purchaseService.withdrawPurchase(loginMemberInfo.getMemberId(),
            purchasesId);
        return new ResponseEntity<>(success(response), HttpStatus.OK);
    }
}
