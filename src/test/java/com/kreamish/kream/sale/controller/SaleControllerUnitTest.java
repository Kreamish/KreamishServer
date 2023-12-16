package com.kreamish.kream.sale.controller;

import static com.kreamish.kream.login.resolver.LoginMemberArgumentResolver.KREAMISH_PASSWORD;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import com.kreamish.kream.sale.dto.PendingSaleDto;
import com.kreamish.kream.sale.dto.PendingSaleResponseDto;
import com.kreamish.kream.sale.service.SaleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(SpringExtension.class)
public class SaleControllerUnitTest {

    private static final String BASE_URL = "/sale";
    private final String anyId = "1";
    WebTestClient webTestClient;
    @Mock
    SaleService saleService;
    @InjectMocks
    SaleController saleController;
    Map<String, String> params = new HashMap<>();

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
            Arguments.of("a", "1", HttpStatus.BAD_REQUEST.value(), 0),
            Arguments.of("1", "a", HttpStatus.BAD_REQUEST.value(), 0),
            Arguments.of("1", "1", HttpStatus.OK.value(), 1)
        );
    }

    @BeforeEach
    void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(saleController)
            .controllerAdvice(new GeneralExceptionHandler())
            .customArgumentResolvers(new LoginMemberArgumentResolver())
            .build();
    }

    @ParameterizedTest
    @DisplayName("실패(1,2), 성공(3): 판매 입찰 중인 판매 리스트 가져오기. 잘못된 Path variable 값 전달(1,2), 올바른 파라미터 전달(3)")
    @MethodSource("provideArgs")
    void TEST_GET_PENDING_SALE_SHOULD_CHECK_PATH_VARIABLE(String itemId, String itemSizesId,
        int httpStatusCode, int mockExecutionCnt) {
        final String uri = "/item/{item-id}/itemsizes/{item-sizes-id}";

        params.put("item-id", itemId);
        params.put("item-sizes-id", itemSizesId);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.setBasicAuth(anyId, KREAMISH_PASSWORD))
            .exchange()

            .expectStatus()
            .isEqualTo(httpStatusCode);

        verify(saleService, times(mockExecutionCnt)).getPendingSaleByItemSizesId(
            anyLong());
    }

    @Test
    @DisplayName("성공: 판매 입찰중인 리스트 가져오기. DTO JsonPath 올바른지 확인")
    void SUCCESS_GET_PENDING_SALE_SHOUL_CHECK_DTO_JSON_PATH() {
        final String uri = "/item/{item-id}/itemsizes/{item-sizes-id}";
        final PendingSaleDto pendingSaleDto = PendingSaleDto.of("1", 1L, null);
        final PendingSaleResponseDto pendingSaleResponseDto = PendingSaleResponseDto.of(
            List.of(pendingSaleDto));

        params.put("item-id", anyId);
        params.put("item-sizes-id", anyId);

        doReturn(pendingSaleResponseDto).when(saleService)
            .getPendingSaleByItemSizesId(anyLong());

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.setBasicAuth(anyId, KREAMISH_PASSWORD))
            .exchange()

            .expectBody()
            .jsonPath("$.response.pendingSaleDtoList").hasJsonPath()
            .jsonPath("$.response.pendingSaleDtoList").isArray()
            .jsonPath("$.response.pendingSaleDtoList[0].itemSizes").hasJsonPath()
            .jsonPath("$.response.pendingSaleDtoList[0].price").hasJsonPath()
            .jsonPath("$.response.pendingSaleDtoList[0].quantity").hasJsonPath();
    }
}
