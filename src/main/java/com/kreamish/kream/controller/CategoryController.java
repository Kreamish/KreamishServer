package com.kreamish.kream.controller;

import com.kreamish.kream.common.ApiUtils;
import com.kreamish.kream.common.ApiUtils.ApiResult;
import com.kreamish.kream.dto.CategoriesDto;
import com.kreamish.kream.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(
        summary = "모든 category list",
        description = "모든 category list를 반환"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "category list no content"),
        @ApiResponse(responseCode = "200", description = "category list 정상 반환"),
        @ApiResponse(responseCode = "404", description = "category service error")
    })
    public ResponseEntity<ApiResult<?>> getItems() {
        CategoriesDto findCategories = categoryService.getItems();
        HttpStatus httpStatus = findCategories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;

        return new ResponseEntity<>(ApiUtils.success(findCategories), httpStatus);
    }


}
