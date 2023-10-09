package com.kreamish.kream.item.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.item.service.ItemService;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(MockitoExtension.class)
public class ItemDetailControllerUnitTest {

    final String BASE_URL = "/items";
    final Map<String, String> params = new HashMap<>();
    @Mock
    ItemService itemService;
    @InjectMocks
    ItemDetailController itemDetailController;
    WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(itemDetailController)
            .controllerAdvice(new GeneralExceptionHandler())
            .customArgumentResolvers(new LoginMemberArgumentResolver())
            .build();
    }

    @Test
    @DisplayName("성공 : 아이템 상세 정보 가져오기.")
    void SUCCESS_GET_ITEM_DETAIL_INFO_SHOULD_IS_OK() {
        final String uri = BASE_URL + "/{item-id}";
        final String itemId = "1";

        params.put("item-id", itemId);

        try (MockedStatic<ApiUtils> apiUtilsMockedStatic = mockStatic(ApiUtils.class)) {
            when(itemService.findItemById(anyLong())).thenReturn(null);

            webTestClient.get()
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()

                .expectStatus()
                .isOk();

            verify(itemService, times(1)).findItemById(anyLong());
            apiUtilsMockedStatic.verify(() -> ApiUtils.success(any()), times(1));
            apiUtilsMockedStatic.verify(() -> ApiUtils.error(anyString(), any(HttpStatus.class)),
                times(0));
        }
    }

    @Test
    @DisplayName("실패 : 아이템 상세 정보 가져오기. 존재하지 않는 아이템 아이디")
    void FAIL_GET_ITEM_DETAIL_INFO_SHOULD_IS_NOT_FOUND() {
        final String uri = BASE_URL + "/{item-id}";
        final String itemId = "1";

        params.put("item-id", itemId);

        try (MockedStatic<ApiUtils> apiUtilsMockedStatic = mockStatic(ApiUtils.class)) {
            when(itemService.findItemById(anyLong())).thenThrow(new NoSuchElementException("err"));

            webTestClient.get()
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()

                .expectStatus()
                .isNotFound();

            verify(itemService, times(1)).findItemById(anyLong());
            apiUtilsMockedStatic.verify(() -> ApiUtils.success(any()), times(0));
            apiUtilsMockedStatic.verify(() -> ApiUtils.error(anyString(), any(HttpStatus.class)),
                times(1));
        }
    }

    @Test
    @DisplayName("실패 : 아이템 상세 정보 가져오기. String 타입의 아이템 아이디")
    void FAIL_GET_ITEM_DETAIL_INFO_SHOULD_IS_BAD_REQUEST() {
        final String uri = BASE_URL + "/{item-id}";
        final String itemId = "S";

        params.put("item-id", itemId);

        try (MockedStatic<ApiUtils> apiUtilsMockedStatic = mockStatic(ApiUtils.class)) {
            webTestClient.get()
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();

            verify(itemService, times(0)).findItemById(anyLong());
            apiUtilsMockedStatic.verify(() -> ApiUtils.success(any()), times(0));
            apiUtilsMockedStatic.verify(() -> ApiUtils.error(anyString(), any(HttpStatus.class)),
                times(1));
        }
    }
}

