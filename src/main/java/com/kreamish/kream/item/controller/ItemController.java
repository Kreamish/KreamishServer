package com.kreamish.kream.item.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @Operation(
        summary = "필터링 적용한 상품 목록 반환",
        description = "카테고리, 브랜드, 컬렉션 id, 최소/최대 금액 등을 적용한 페이징 처리된 상품 목록 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "존재하지 않는 리소스"),
        @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    @Parameters
    public ResponseEntity<ApiUtils.ApiResult<ItemListResponseDto>> getItems(
        ItemListSearchCondition condition
    ) {
        PageRequest pageRequest = PageRequest.of(condition.getPage(), condition.getPageSize());

        ItemListResponseDto itemListResponseDto = itemService.findItemsByCondition(condition, pageRequest);

        HttpStatus responseStatus = itemListResponseDto.getItemPages().getTotalElements() == 0L ?
            HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(success(itemListResponseDto), responseStatus);
    }
}
