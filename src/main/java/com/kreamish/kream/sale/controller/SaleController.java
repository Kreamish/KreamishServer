package com.kreamish.kream.sale.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import com.kreamish.kream.sale.dto.PendingSaleResponseDto;
import com.kreamish.kream.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    @GetMapping(value = {"/item/{item-id}"})
    public ResponseEntity<ApiResult<PendingSaleResponseDto>> getPendingSaleByItemId(
        @Login LoginMemberInfo loginMemberInfo, @PathVariable("item-id") Long itemId) {
        PendingSaleResponseDto pendingSaleResponseDto = saleService.getPendingSalesByItemId(
            itemId);

        return ResponseEntity.ok(success(pendingSaleResponseDto));
    }

    @GetMapping(value = {"/item/{item-id}/itemsizes/{item-sizes-id}"})
    public ResponseEntity<ApiResult<PendingSaleResponseDto>> getPendingSaleByItemId(
        @Login LoginMemberInfo loginMemberInfo, @PathVariable("item-id") Long itemId,
        @PathVariable(value = "item-sizes-id", required = false) Long itemSizesId) {
        PendingSaleResponseDto pendingSaleResponseDto = saleService.getPendingSaleByItemSizesId(
            itemSizesId);

        return ResponseEntity.ok(success(pendingSaleResponseDto));
    }


}
