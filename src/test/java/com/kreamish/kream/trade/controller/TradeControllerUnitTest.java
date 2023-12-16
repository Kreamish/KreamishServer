package com.kreamish.kream.trade.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import com.kreamish.kream.trade.converter.PeriodConverter;
import com.kreamish.kream.trade.dto.TradeHistoryResponseDto;
import com.kreamish.kream.trade.dto.TradeOrderDto;
import com.kreamish.kream.trade.enums.Period;
import com.kreamish.kream.trade.service.TradeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(MockitoExtension.class)
public class TradeControllerUnitTest {

    final String BASE_URL = "/trades";
    final String KREMAISH = "kreamish";
    final Map<String, String> params = new HashMap<>();
    @Mock
    TradeService tradeService;
    @InjectMocks
    TradeController tradeController;
    WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addConverter(new PeriodConverter());

        this.webTestClient = MockMvcWebTestClient
            .bindToController(tradeController)
            .controllerAdvice(new GeneralExceptionHandler())
            .customArgumentResolvers(new LoginMemberArgumentResolver())
            .conversionService(formattingConversionService)
            .build();
    }

    @Test
    @DisplayName("실패: 시세 그래프 가져오기. 미로그인 유저가 api 호출")
    void FAIL_GET_MARKET_PRICES_GRAPH_SHOULD_401() {
        final String itemId = "1";
        final String uri = "/items/{item-id}/chart?period=all";

        params.put("item-id", itemId);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

            .expectStatus()
            .isUnauthorized();

        verify(tradeService, never()).getMarketPricesGraph(anyLong(), any(Period.class));
    }

    @Test
    @DisplayName("실패: 시세 그래프 가져오기. 잘못된 타입의 아이템 id")
    void FAIL_GET_MARKET_PRICES_GRAPH_SHOULD_400() {
        final String wrongItemId = "a";
        final String memberId = "1";
        final String uri = "/items/{item-id}/chart?period=1";

        params.put("item-id", wrongItemId);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isBadRequest();

        verify(tradeService, never()).getMarketPricesGraph(anyLong(), any(Period.class));
    }

    @ParameterizedTest
    @DisplayName("실패: 시세 그래프 가져오기. 잘못된 기간 값으로 조회")
    @ValueSource(strings = {"2", " ", "lorem"})
    void FAIL_GET_MARKET_PRICES_GRAPH_SHOULD_400(String period) {
        final String itemId = "1";
        final String memberId = "1";
        final String uri = "/items/{item-id}/chart?period={period}";

        params.put("item-id", itemId);
        params.put("period", period);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isBadRequest()
            .expectBody().consumeWith(System.out::println);

        verify(tradeService, never()).getMarketPricesGraph(anyLong(), any(Period.class));
    }

    @ParameterizedTest
    @DisplayName("성공: 시세 그래프 가져오기.")
    @ValueSource(strings = {"1", "3", "6", "12", "all"})
    void SUCCESS_GET_MARKET_PRICES_GRAPH_SHOULD_400(String period) {
        final String itemId = "1";
        final String memberId = "2";
        final String uri = "/items/{item-id}/chart?period={period}";

        params.put("item-id", itemId);
        params.put("period", period);

        when(tradeService.getMarketPricesGraph(anyLong(), any(Period.class))).thenReturn(null);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isOk();

        verify(tradeService, times(1)).getMarketPricesGraph(anyLong(), any(Period.class));
    }

    @Test
    @DisplayName("실패: 체결 거래 내역 가져오기. 잘못된 타입의 id")
    void FAIL_GET_TRADE_HISTORY_BY_ITEM_ID_SHOULD_CHECK_IS_BAD_REQUEST() {
        final String wrongItemId = "a";
        final String memberId = "1";
        final String uri = "/items/{item-id}";

        params.put("item-id", wrongItemId);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isBadRequest();

        verify(tradeService, never()).getTradeHistoryByItemId(anyLong());
    }

    @Test
    @DisplayName("성공: 아이템 ID로 체결 거래 내역 가져오기. DTO json path 확인")
    void FAIL_GET_TRADE_HISTORY_BY_ITEM_ID_SHOULD_CHECK_RESPONSE_DTO_JSON_PATH() {
        final String itemId = "1";
        final Long longItemId = 1L;
        final String memberId = "1";
        final String uri = "/items/{item-id}";

        final TradeHistoryResponseDto tradeHistoryResponseDto = TradeHistoryResponseDto.of(
            List.of(new TradeOrderDto()), longItemId);

        params.put("item-id", itemId);

        when(tradeService.getTradeHistoryByItemId(anyLong())).thenReturn(tradeHistoryResponseDto);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response.tradeOrderDtoList")
            .isArray()

            .jsonPath("$.response.tradeOrderDtoList[0].itemSizesId")
            .hasJsonPath()

            .jsonPath("$.response.tradeOrderDtoList[0].itemSizes")
            .hasJsonPath()

            .jsonPath("$.response.tradeOrderDtoList[0].price")
            .hasJsonPath()

            .jsonPath("$.response.tradeOrderDtoList[0].tradedAt")
            .hasJsonPath()

            .jsonPath("$.response.itemId")
            .hasJsonPath()

            .jsonPath("$.error")
            .isEmpty();

        verify(tradeService, times(1)).getTradeHistoryByItemId(anyLong());
    }

    @Test
    @DisplayName("성공: 아이템 사이즈 ID로 체결 거래 내역 가져오기. DTO json path 확인")
    void FAIL_GET_TRADE_HISTORY_BY_ITEM_SIZES_ID_SHOULD_CHECK_RESPONSE_DTO_JSON_PATH() {
        final String itemId = "1";
        final Long longItemId = 1L;
        final String itemSizesId = "1";
        final String memberId = "1";
        final String uri = "/items/{item-id}/item-sizes/{item-sizes-id}";
        final TradeHistoryResponseDto tradeHistoryResponseDto = TradeHistoryResponseDto.of(
            List.of(new TradeOrderDto()), longItemId);

        params.put("item-id", itemId);
        params.put("item-sizes-id", itemSizesId);

        when(tradeService.getTradeHistoryByItemSizesId(anyLong(), anyLong())).thenReturn(
            tradeHistoryResponseDto);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response.tradeOrderDtoList")
            .isArray()

            .jsonPath("$.response.tradeOrderDtoList[0].itemSizesId")
            .hasJsonPath()

            .jsonPath("$.response.tradeOrderDtoList[0].itemSizes")
            .hasJsonPath()

            .jsonPath("$.response.tradeOrderDtoList[0].price")
            .hasJsonPath()

            .jsonPath("$.response.tradeOrderDtoList[0].tradedAt")
            .hasJsonPath()

            .jsonPath("$.response.itemId")
            .hasJsonPath()

            .jsonPath("$.error")
            .isEmpty();

        verify(tradeService, times(1)).getTradeHistoryByItemSizesId(anyLong(), anyLong());
    }
}
