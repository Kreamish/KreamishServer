package com.kreamish.kream.item.controller;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.item.dto.ItemDetailResponseDto;
import com.kreamish.kream.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemDetailController {

    private final ItemService itemService;

    @Operation(
        summary = "상품 세부에 대한 정보 반환",
        description = "itemId 로 상품에 대한 상세 정보 조회 및 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @GetMapping("{item-id}")
    public ResponseEntity<ApiUtils.ApiResult<ItemDetailResponseDto>> getItemDetail(
        @PathVariable("item-id") Long itemId
    ) {
        ItemDetailResponseDto dto = itemService.findItemById(itemId);

        return new ResponseEntity<>(ApiUtils.success(dto), HttpStatus.OK);
    }
}
