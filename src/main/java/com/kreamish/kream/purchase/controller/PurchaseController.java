package com.kreamish.kream.purchase.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.purchase.dto.PendingPurchaseResponseDto;
import com.kreamish.kream.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping(value = {"/item/{item-id}"})
    public ResponseEntity<ApiResult<PendingPurchaseResponseDto>> getPendingPurchasesByItemId(
        @Login LoginMemberInfo loginMemberInfo, @PathVariable("item-id") Long itemId) {
        PendingPurchaseResponseDto pendingPurchaseResponseDto = purchaseService.getPendingPurchasesByItemId(
            itemId);

        return ResponseEntity.ok(success(pendingPurchaseResponseDto));
    }

    @GetMapping(value = {"/item/{item-id}/itemsizes/{item-sizes-id}"})
    public ResponseEntity<ApiResult<PendingPurchaseResponseDto>> getPendingPurchasesByItemId(
        @Login LoginMemberInfo loginMemberInfo, @PathVariable("item-id") Long itemId,
        @PathVariable(value = "item-sizes-id", required = false) Long itemSizesId) {
        PendingPurchaseResponseDto pendingPurchaseResponseDto = purchaseService.getPendingPurchasesByItemSizesId(
            itemSizesId);

        return ResponseEntity.ok(success(pendingPurchaseResponseDto));
    }
}
