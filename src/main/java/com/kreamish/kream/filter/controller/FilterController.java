package com.kreamish.kream.filter.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.collection.dto.CollectionDto;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.filter.dto.BrandFilterResponseDto;
import com.kreamish.kream.filter.dto.CategoriesFilterResponseDto;
import com.kreamish.kream.filter.dto.ItemSizesFilterResponseDto;
import com.kreamish.kream.filter.facade.FilterFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

    private final FilterFacade filterFacade;

    @GetMapping("/categories")
    @Operation(
        summary = "카테고리 별 세부 카테고리 리스트 반환",
        description = "필터링을 위한 모든 카테고리 별 세부 카테고리 리스트 반환"
    )

    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "필터링을 위한 리스트 정상 반환"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<ApiResult<List<CategoriesFilterResponseDto>>> getCategories() {
        return new ResponseEntity<>(success(filterFacade.getCategories()), HttpStatus.OK);
    }

    @GetMapping(value = "/brand", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "브랜드 리스트 반환",
        description = "필터링을 위한 모든 브랜드 리스트 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "필터링을 위한 리스트 정상 반환"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<ApiResult<List<BrandFilterResponseDto>>> getBrand() {
        return new ResponseEntity<>(success(filterFacade.getBrandFilterList()), HttpStatus.OK);
    }

    @GetMapping(value = "/collections", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "컬렉션 리스트 반환",
        description = "필터링을 위한 모든 컬렉션 리스트 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "필터링을 위한 리스트 정상 반환"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<ApiResult<List<CollectionDto>>> getCollections() {
        return new ResponseEntity<>(success(filterFacade.getCollections()), HttpStatus.OK);
    }

    @GetMapping(value = "/itemsizes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "아이템 사이즈 리스트 반환",
        description = "필터링을 위한 모든 아이템 사이즈 리스트 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "필터링을 위한 리스트 정상 반환"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<ApiResult<List<ItemSizesFilterResponseDto>>> getItemSizes() {
        return new ResponseEntity<>(success(filterFacade.getItemSizesFilterList()), HttpStatus.OK);
    }
}
