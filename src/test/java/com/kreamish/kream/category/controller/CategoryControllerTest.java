package com.kreamish.kream.category.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.kreamish.kream.category.dto.CategoriesDto;
import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.category.service.CategoryService;
import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.common.util.ApiUtils;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;
    WebTestClient webTestClient;
    @InjectMocks
    CategoryController categoryController;

    @BeforeEach
    void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(categoryController)
            .controllerAdvice(new GeneralExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("성공: 모든 List 반환. status 200")
    void SUCCESS_SHOULD_CHECK_STATUS_200() {
        Category mockBrand = Category.of("mockBrand");
        CategoriesDto mockCategoriesDto = CategoriesDto.of(List.of(mockBrand));

        doReturn(mockCategoriesDto).when(categoryService).getAllCategories();

        webTestClient.get()
            .uri("/categories")
            .exchange()
            .expectStatus().isOk()
            .expectBody(ApiUtils.ApiResult.class);
    }

    @Test
    @DisplayName("성공: Category List가 존재하지 않음. status 204")
    void SUCCESS_SHOULD_CHECK_STATUS_204() {
        CategoriesDto mockCategoriesDto = CategoriesDto.of(Collections.EMPTY_LIST);

        doReturn(mockCategoriesDto).when(categoryService).getAllCategories();

        webTestClient.get()
            .uri("/categories")
            .exchange()
            .expectStatus().isNoContent()
            .expectBody(ApiUtils.ApiResult.class);
    }

    @Test
    @DisplayName("실패: 40x 에러 발생")
    void FAIL_SHOULD_CHECK_STATUS_40x() {
        doThrow(RuntimeException.class).when(categoryService).getAllCategories();

        webTestClient.get()
            .uri("/categories")
            .exchange()
            .expectStatus().is4xxClientError()
            .expectBody(ApiUtils.ApiResult.class);
    }
}