package com.kreamish.kream.trade.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import com.kreamish.kream.trade.enums.Period;
import com.kreamish.kream.trade.service.TradeService;
import java.util.HashMap;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(MockitoExtension.class)
public class TradeControllerUnitTest {

    final String BASE_URL = "/trade";
    final String KREMAISH = "kreamish";
    final Map<String, String> params = new HashMap<>();
    @Mock
    TradeService tradeService;
    @InjectMocks
    TradeController tradeController;
    WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(tradeController)
            .controllerAdvice(new GeneralExceptionHandler())
            .customArgumentResolvers(new LoginMemberArgumentResolver())
            .build();
    }

    @Test
    @DisplayName("실패: 시세 그래프 가져오기. 미로그인 유저가 api 호출")
    void FAIL_GET_MARKET_PRICES_GRAPH_SHOULD_401() {
        final String itemId = "1";
        final String uri = "/{item-id}/chart?period=all";

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
        final String uri = "/{item-id}/chart?period=1";

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
        final String uri = "/{item-id}/chart?period={period}";

        params.put("item-id", itemId);
        params.put("period", period);

        webTestClient.get()
            .uri(BASE_URL + uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREMAISH))
            .exchange()

            .expectStatus()
            .isBadRequest();

        verify(tradeService, never()).getMarketPricesGraph(anyLong(), any(Period.class));
    }

}
