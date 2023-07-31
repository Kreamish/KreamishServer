package com.kreamish.kream.item.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.item.dto.ItemListResponseDto;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Parameters({
        @Parameter(name = "category-ids", description = "카테고리 id 목록.",
            examples = {
                @ExampleObject(name = "multiple", value = "1,2,3", summary = "1,2,3", description = "category-id 가 [1,2,3] 에 속하는 item 필터링"),
                @ExampleObject(name = "singleton", value = "1", summary = "1", description = "category-id 가 1 인 item 필터링"),
                @ExampleObject(name = "none", summary = "조건 없는 경우", description = "모든 category-id 로 조회")
            }
        ),
        @Parameter(name = "brand-ids", description = "브랜드 id 목록.",
            examples = {
                @ExampleObject(name = "multiple", value = "1,2,3", summary = "1,2,3", description = "brand-id 가 [1,2,3] 에 속하는 item 필터링"),
                @ExampleObject(name = "singleton", value = "1", summary = "1", description = "brand-id 가 1인  item 필터링"),
                @ExampleObject(name = "none", summary = "조건 없는 경우", description = "모든 brand-id 로 조회")
            }
        ),
        @Parameter(name = "collection-ids", description = "컬렉션 id 목록.",
            examples = {
                @ExampleObject(name = "multiple", value = "1,2,3", summary = "1,2,3", description = "collection-id 가 [1,2,3] 에 속하는 item 필터링"),
                @ExampleObject(name = "singleton", value = "1", summary = "1", description = "collection-id 가 1인  item 필터링"),
                @ExampleObject(name = "none", summary = "조건 없는 경우", description = "모든 collection-id 로 조회")
            }
        ),
        @Parameter(name = "min-price", description = "최소 가격 (최근 거래 기준) 현재 데이터가 없어, 반영 X", deprecated = true,
            examples = {
                @ExampleObject(name = "none", summary = "조건 없는 경우", description = "가격 필터링 적용 X"),
//                @ExampleObject(name = "singleton", value = "150000", summary = "min-price: 150000", description = "최소 가격")
            }
        ),
        @Parameter(name = "max-price", description = "최대 가격 (최근 거래 기준) 현재 데이터가 없어, 반영 X", deprecated = true,
            examples = {
                @ExampleObject(name = "none", summary = "조건 없는 경우", description = "가격 필터링 적용 X"),
//                @ExampleObject(name = "singleton", value = "200000", summary = "max-price: 150000", description = "최대 가격")
            }
        ),
        @Parameter(name = "sizes", description = "size 목록. 현재 데이터가 없어, 반영 X", deprecated = true,
            examples = {
//                @ExampleObject(name = "multiple", value = "95,100,105", summary = "min-price: 150000", description = "sizes 가 [95,100,105] 속하는 경우"),
//                @ExampleObject(name = "singleton", value = "100", summary = "min-price: 150000", description = "sizes 가 100 인 경우."),
                @ExampleObject(name = "none", summary = "조건 없는 경우", description = "size 필터링 적용 X")
            }
        ),
        @Parameter(name = "page", description = "받고자 하는 페이지의 번호 default: 0"),
        @Parameter(name = "size", description = "한 페이지 당 개수 default: 10")
    })
    public ResponseEntity<ApiUtils.ApiResult<ItemListResponseDto>> getItems(
        @Schema(hidden = true) ItemListSearchCondition condition
    ) {
        PageRequest pageRequest = PageRequest.of(condition.getPage(), condition.getPageSize());

        ItemListResponseDto itemListResponseDto = itemService.findItemsByCondition(condition, pageRequest);

        HttpStatus responseStatus = itemListResponseDto.getItemPages().getTotalElements() == 0L ?
            HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(success(itemListResponseDto), responseStatus);
    }
}
