package com.kreamish.kream.item.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ApiUtils.ApiResult<ItemListResponseDto>> getItems(
        @RequestParam(value = "category-id", required = false) List<Long> categoryIds,
        @RequestParam(value = "brand-id", required = false) List<Long> brandIds,
        @RequestParam(value = "collection-id", required = false) List<Long> collectionIds,
        @RequestParam(value = "min-price", required = false) Long minPrice,
        @RequestParam(value = "max-price", required = false) Long maxPrice,
        @RequestParam(value = "size", required = false) List<String> sizes,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
        @RequestParam(value = "sort", required = false) String sort,
        @RequestParam(value = "sort-dir", required = false) String sortDir
    ) {

        ItemListSearchCondition condition = ItemListSearchCondition.builder()
            .categoryIds(categoryIds)
            .brandIds(brandIds)
            .collectionIds(collectionIds)
            .minPrice(minPrice)
            .maxPrice(maxPrice)
            .sizes(sizes)
            .build();

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        ItemListResponseDto itemListResponseDto = itemService.findItemsByCondition(condition, pageRequest);

        HttpStatus responseStatus = itemListResponseDto.getItemPages().getTotalElements() == 0L ?
            HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(success(itemListResponseDto), responseStatus);
    }
}
