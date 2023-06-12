package com.kreamish.kream.filter.controller;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.filter.dto.CategoryDetailFilterResultDto;
import com.kreamish.kream.filter.facade.FilterFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kreamish.kream.common.util.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
    private final FilterFacade filterFacade;
    @GetMapping("/categories")
    public ResponseEntity<ApiUtils.ApiResult<CategoryDetailFilterResultDto>> getCategories() {
        return new ResponseEntity<>(success(filterFacade.getCategories()), HttpStatus.OK);
    }
}
