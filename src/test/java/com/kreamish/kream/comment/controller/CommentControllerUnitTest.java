package com.kreamish.kream.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.kreamish.kream.comment.dto.CommentRequestDto;
import com.kreamish.kream.comment.facade.CommentFacade;
import com.kreamish.kream.common.error.GeneralExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class CommentControllerUnitTest {

    @MockBean
    CommentFacade commentFacade;
    @InjectMocks
    CommentController commentController;
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(commentController)
            .controllerAdvice(new GeneralExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("실패: 댓글 등록 실패. item_id가 null")
    void FAIL_SHOULD_CHECK_STATUS_400() {
        Map<String, String> params = new HashMap();
        params.put("member_id", "1");
        params.put("value", "123123");

        webTestClient.post()
            .uri("/comment")
            .body(BodyInserters.fromValue(params))

            .exchange()
            .expectStatus().isBadRequest()
            .expectBody();
    }

    @Test
    @DisplayName("성공: 댓글 등록")
    void SUCCESS_SHOULD_CHECK_STATUS_200() {
        Map<String, String> params = new HashMap();
        params.put("member_id", "1");
        params.put("item_id", "1");
        params.put("value", "comment content");

        doReturn(null).when(commentFacade).create(any(CommentRequestDto.class));

        webTestClient.post()
            .uri("/comment")
            .body(BodyInserters.fromValue(params))

            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.error").doesNotExist();
    }

    @Test
    @DisplayName("실패: 댓글 삭제 실패. comment-id type mismatch")
    void FAIL_SHOULD_CHECK_REQUIRED_PATH_VARIABLE() {
        webTestClient.delete()
            .uri("/comment/mismatch/member/1")

            .exchange()
            .expectStatus().is4xxClientError();
            
    }
}