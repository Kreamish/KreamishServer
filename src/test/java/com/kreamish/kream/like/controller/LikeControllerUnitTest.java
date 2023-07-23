package com.kreamish.kream.like.controller;

import com.kreamish.kream.common.error.GeneralExceptionHandler;
import com.kreamish.kream.like.facade.LikeFacade;
import java.util.HashMap;
import java.util.Map;
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
class LikeControllerUnitTest {

    @Mock
    LikeFacade likeFacade;
    WebTestClient webTestClient;
    @InjectMocks
    LikeController likeController;
    Map<String, String> params = new HashMap<>();

    @BeforeEach
    void SET_UP() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(likeController)
            .controllerAdvice(new GeneralExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("성공: post 좋아요 표시. 필수 파라미터 체크하기")
    void SUCCESS_CLICK_LIKE_SHOULD_IS_OK() {
        String uri = "/like";
        String itemId;
        String memberId;

        params.put("item-id", itemId);
        params.put("member-id", memberId);

        // 아이템 id, 멤버 id 바디에 존재하는지 확인

    }
}