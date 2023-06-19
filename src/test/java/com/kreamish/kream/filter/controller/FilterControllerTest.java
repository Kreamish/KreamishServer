package com.kreamish.kream.filter.controller;

import static org.mockito.Mockito.doReturn;

import com.kreamish.kream.filter.facade.FilterFacade;
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
class FilterControllerTest {

    @Mock
    FilterFacade filterFacade;
    WebTestClient webTestClient;
    @InjectMocks
    FilterController filterController;

    @BeforeEach
    void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(filterController)
            .build();
    }

    @Test
    @DisplayName("성공: 모든 필터링 카테고리 목록 반환")
    void SUCCESS_SHOULD_RETURN_CATEGORY_DETAIL_LIST_FOR_FILTER() {
        doReturn(null).when(filterFacade).getCategories();

        webTestClient.get()
            .uri("/filter/categories")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.error").doesNotExist();
    }

    @Test
    @DisplayName("성공: 모든 브랜드 리스트 반환")
    void SUCCESS_SHOULD_RETURN_BRAND_LIST_FOR_FILTER() {
        doReturn(null).when(filterFacade).getBrand();

        webTestClient.get()
            .uri("/filter/brand")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.error").doesNotExist();
    }

    @Test
    @DisplayName("성공: 모든 컬렉션 리스트 반환")
    void SUCCESS_SHOULD_RETURN_COLLECTION_LIST_FOR_FILTER() {
        doReturn(null).when(filterFacade).getCollections();

        webTestClient.get()
            .uri("/filter/collections")
            .exchange()
            .expectStatus().isOk()
            .expectBody()

            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.error").doesNotExist();
    }
}