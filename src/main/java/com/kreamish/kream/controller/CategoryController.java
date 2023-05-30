package com.kreamish.kream.controller;

import com.kreamish.kream.common.ApiUtils.ApiResult;
import com.kreamish.kream.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping(value = "/all")
  public ResponseEntity<ApiResult<?>> getItems() {
    return categoryService.getItems();
  }
}
