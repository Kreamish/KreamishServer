package com.kreamish.kream.purchase.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import com.kreamish.kream.purchase.dto.PurchaseDeleteResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseDetailResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseListResponseDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterRequestDto;
import com.kreamish.kream.purchase.dto.PurchaseRegisterResponseDto;
import com.kreamish.kream.purchase.service.PurchaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    @Mock
    PurchaseService purchaseService;

    @InjectMocks
    PurchaseController purchaseController;

    WebTestClient webTestClient;

    final String KREAMISH = "kreamish";
    final Long memberId = 1L;

    @BeforeEach
    void SET_UP() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(purchaseController)
            .customArgumentResolvers(new LoginMemberArgumentResolver())
            .controllerAdvice(new GeneralExceptionHandler())
            .build();
    }

    @Test
    void SUCCESS_CREATE_PURCHASE_SIMPLE() {
        final Long itemSizesId = 111L;
        final Long price = 10000L;
        final Long purchaseId = 123L;
        final String uri = "/purchases/item-sizes/" + itemSizesId;

        when(purchaseService.createPurchaseAndProceedTrade(memberId, itemSizesId, price))
            .thenReturn(new PurchaseRegisterResponseDto(purchaseId, null));

        PurchaseRegisterRequestDto dto = new PurchaseRegisterRequestDto();
        dto.setPrice(price);

        ApiResult<?> apiResult = webTestClient.post()
            .uri(uri)
            .body(BodyInserters.fromValue(dto))
            .headers(h -> h.setBasicAuth(memberId.toString(), KREAMISH))
            .exchange()

            .expectStatus()
            .isCreated()

            .expectBody(ApiResult.class)
            .returnResult()
            .getResponseBody();

        assertThat(apiResult).isNotNull();
        assertThat(apiResult.isSuccess()).isTrue();
        assertThat(apiResult.getError()).isNull();
        assertThat(apiResult.getResponse()).isNotNull();
    }

    @Test
    void SUCCESS_GET_PURCHASES_SIMPLE() {
        Long purchaseId = 123L;
        Long itemSizesId = 1234L;
        Long price = 11000L;
        final String uri = "/purchases/";

        List<PurchaseDetailResponseDto> list = new ArrayList<>();
        PurchaseListResponseDto response = new PurchaseListResponseDto(list);
        PurchaseDetailResponseDto item = new PurchaseDetailResponseDto(
            purchaseId,
            itemSizesId,
            memberId,
            price,
            DealStatus.PENDING
        );
        list.add(item);
        when(purchaseService.findPurchasesByMemberId(memberId, null))
            .thenReturn(response);

        ApiResult<PurchaseListResponseDto> result = webTestClient.get()
            .uri(uri)
            .headers(h -> h.setBasicAuth(memberId.toString(), KREAMISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody(new ParameterizedTypeReference<ApiResult<PurchaseListResponseDto>>() {
            })
            .returnResult()
            .getResponseBody();

        assertThat(result).isNotNull();
        assertThat(result.getResponse()).isNotNull();
        List<PurchaseDetailResponseDto> purchaseList = result.getResponse().getPurchases();
        assertThat(purchaseList).hasSize(1);
        assertThat(purchaseList.get(0).getPurchaseId()).isEqualTo(purchaseId);
    }

    @Test
    void SUCCESS_GET_PURCHASES_EMPTY() {
        final String uri = "/purchases";

        PurchaseListResponseDto response = new PurchaseListResponseDto(new ArrayList<>());
        when(purchaseService.findPurchasesByMemberId(memberId, null))
            .thenReturn(response);

        ApiResult<PurchaseListResponseDto> result = webTestClient.get()
            .uri(uri)
            .headers(h -> h.setBasicAuth(memberId.toString(), KREAMISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody(new ParameterizedTypeReference<ApiResult<PurchaseListResponseDto>>() {})
            .returnResult()
            .getResponseBody();

        assertThat(result).isNotNull();
        assertThat(result.getResponse()).isNotNull();
        List<PurchaseDetailResponseDto> purchases = result.getResponse().getPurchases();
        assertThat(purchases).isNotNull().isEmpty();
    }

    @Test
    void SUCCESS_DELETE_PURCHASE(){
        final Long purchaseId = 123L;
        final String uri = "/purchases/" + purchaseId;

        PurchaseDeleteResponseDto response = new PurchaseDeleteResponseDto();
        when(purchaseService.withdrawPurchase(memberId, purchaseId))
            .thenReturn(response);

        ApiResult<PurchaseDeleteResponseDto> responseBody = webTestClient.delete()
            .uri(uri)
            .headers(h -> h.setBasicAuth(memberId.toString(), KREAMISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody(new ParameterizedTypeReference<ApiResult<PurchaseDeleteResponseDto>>() {
            })
            .returnResult()
            .getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getResponse()).isNotNull();
    }

    @Test
    void FAIL_DELETE_PURCHASE_NO_SUCH_EXCEPTION(){
        final Long purchaseId = 123L;
        final String uri = "/purchases/" + purchaseId;

        when(purchaseService.withdrawPurchase(memberId, purchaseId))
            .thenThrow(new NoSuchElementException());

        webTestClient.delete()
            .uri(uri)
            .headers(h -> h.setBasicAuth(memberId.toString(), KREAMISH))
            .exchange()

            .expectStatus()
            .isNotFound();
    }
}