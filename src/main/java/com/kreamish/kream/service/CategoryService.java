package com.kreamish.kream.service;

import com.kreamish.kream.common.ApiUtils.ApiResult;
import com.kreamish.kream.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<ApiResult<?>> getItems() {
        return null;
    }
}
