package com.kreamish.kream.favorite.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.favorite.dto.FavoriteResponseDto;
import com.kreamish.kream.favorite.service.FavoriteService;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(MockitoExtension.class)
class FavoriteControllerUnitTest {

    final String BASE_URI = "/favorites";
    final String KREMAISH = "kreamish";
    final String memberId = "1";
    @Mock
    FavoriteService favoriteService;
    WebTestClient webTestClient;
    @InjectMocks
    FavoriteController favoriteController;
    Map<String, String> params = new HashMap<>();

    @BeforeEach
    void SET_UP() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(favoriteController)
            .customArgumentResolvers(new LoginMemberArgumentResolver())
            .controllerAdvice(new GeneralExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("성공: 관심상품 표시 등록 성공")
    void SUCCESS_CLICK_LIKE_SHOULD_IS_OK() {
        final String uri = BASE_URI + "/item-sizes-id/{item-sizes-id}";
        final String itemSizesId = "1";
        final Long favoriteId = 1L;

        params.put("item-sizes-id", itemSizesId);

        when(favoriteService.createFavorite(anyLong(), anyLong())).thenReturn(
            new FavoriteResponseDto(favoriteId, Long.parseLong(itemSizesId)));

        ApiResult apiResult = webTestClient.post()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isTrue();
        assertThat(apiResult.getError()).isNull();
        assertThat(apiResult.getResponse()).isNotNull();
    }

    @Test
    @DisplayName("실패: 관심상품 표시 실패. 잘못된 타입의 id 값 전달")
    void FAIL_CLICK_LIKE_SHOULD_IS_BAD_REQUEST() {
        final String uri = BASE_URI + "/item-sizes-id/{item-sizes-id}";
        final String invalidId = "invalidId";

        params.put("item-sizes-id", invalidId);

        ApiResult apiResult = webTestClient.post()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isBadRequest()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isFalse();
        assertThat(apiResult.getError()).isNotNull();
        assertThat(apiResult.getResponse()).isNull();
    }

    @Test
    @DisplayName("실패: 관심상품 표시 실패. 존재하지 않는 리소스 아이디 값 전달")
    void FAIL_CLICK_LIKE_SHOULD_IS_NOT_FOUND() {
        final String uri = BASE_URI + "/item-sizes-id/{item-sizes-id}";
        final String notExistedId = "1";

        when(favoriteService.createFavorite(anyLong(), anyLong())).thenThrow(
            new NoSuchElementException());

        params.put("item-sizes-id", notExistedId);

        ApiResult apiResult = webTestClient.post()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isNotFound()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isFalse();
        assertThat(apiResult.getError()).isNotNull();
        assertThat(apiResult.getResponse()).isNull();
    }

    @Test
    @DisplayName("성공: 관심상품 취소 성공")
    void SUCCESS_DELETE_FAVORITES_SHOULD_IS_OK() {
        final String uri = BASE_URI + "/{favorite-id}";
        final String favoriteId = "1";

        params.put("favorite-id", favoriteId);

        doNothing().when(favoriteService).deleteFavorite(anyLong());

        ApiResult apiResult = webTestClient.delete()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isTrue();
        assertThat(apiResult.getError()).isNull();
        assertThat(apiResult.getResponse()).isNull();

        verify(favoriteService, times(1)).deleteFavorite(anyLong());
    }

    @Test
    @DisplayName("실패: 관심상품 취소 실패. 잘못된 타입의 id 값 전달")
    void FAIL_DELETE_FAVORITES_SHOULD_IS_BAD_REQUEST() {
        final String uri = BASE_URI + "/{favorite-id}";
        final String invalidId = "invalidId";

        params.put("favorite-id", invalidId);

        ApiResult apiResult = webTestClient.delete()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isBadRequest()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isFalse();
        assertThat(apiResult.getError()).isNotNull();
        assertThat(apiResult.getResponse()).isNull();

        verify(favoriteService, never()).deleteFavorite(anyLong());
    }

    @Test
    @DisplayName("실패: 관심상품 취소 실패. 존재하지 않는 리소스 아이디 값 전달")
    void FAIL_DELETE_FAVORITES_SHOULD_IS_NOT_FOUND() {
        final String uri = BASE_URI + "/{favorite-id}";
        final String notExistedId = "1";

        doThrow(new NoSuchElementException()).when(favoriteService).deleteFavorite(anyLong());

        params.put("favorite-id", notExistedId);

        ApiResult apiResult = webTestClient.delete()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isNotFound()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isFalse();
        assertThat(apiResult.getError()).isNotNull();
        assertThat(apiResult.getResponse()).isNull();

        verify(favoriteService, times(1)).deleteFavorite(anyLong());
    }

    @Test
    @DisplayName("성공: 관심상품 개수 가져오기 성공")
    void SUCCESS_GET_FAVORITES_CNT_SHOULD_IS_OK() {
        final String uri = BASE_URI + "/count/item/{item-id}";
        final String itemId = "1";
        final Long dummyCnt = 1L;

        params.put("item-id", itemId);

        when(favoriteService.getFavoriteCnt(anyLong())).thenReturn(dummyCnt);

        ApiResult apiResult = webTestClient.get()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isTrue();
        assertThat(apiResult.getResponse()).isNotNull();
        assertThat(apiResult.getError()).isNull();

        verify(favoriteService, times(1)).getFavoriteCnt(anyLong());
    }

    @Test
    @DisplayName("실패: 관심상품 개수 가져오기 실패. 부적합한 Id type 전달")
    void FAIL_GET_FAVORITES_CNT_SHOULD_IS_BAD_REQUEST() {
        final String uri = BASE_URI + "/count/item/{item-id}";
        final String invalidId = "invalidId";

        params.put("item-id", invalidId);

        ApiResult apiResult = webTestClient.get()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isBadRequest()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult.isSuccess()).isFalse();
        assertThat(apiResult.getResponse()).isNull();
        assertThat(apiResult.getError()).isNotNull();

        verify(favoriteService, never()).getFavoriteCnt(anyLong());
    }
}