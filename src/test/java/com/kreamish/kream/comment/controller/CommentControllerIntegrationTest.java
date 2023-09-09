package com.kreamish.kream.comment.controller;

import static com.kreamish.kream.TestDataRunner.ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2;
import static com.kreamish.kream.login.resolver.LoginMemberArgumentResolver.KREAMISH_PASSWORD;

import com.kreamish.kream.TestDataRunner;
import com.kreamish.kream.comment.repository.CommentRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "data"})
@Transactional
public class CommentControllerIntegrationTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    WebTestClient webTestClient;
    Map<String, String> params = new HashMap<>();
    String notExistedId = String.valueOf(Integer.MAX_VALUE);

    @Test
    @DisplayName("실패: member-id 파라미터 없이 댓글 지우기. ")
    void FAIL_DELETE_COMMENT_SHOULD_CHECK_IS_BAD_REQUEST() {
        String uri = "/comments/{comment-id}";
        String commentId = TestDataRunner.COMMENT1_BY_ITEM1_MEMBER1.getCommentId().toString();

        params.put("comment-id", commentId);

        webTestClient.delete()
            .uri(uri, params)
            .exchange()

            .expectStatus()
            .isBadRequest()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(false)

            .jsonPath("$.response")
            .isEmpty()

            .jsonPath("$.error")
            .isNotEmpty();
    }

    @Test
    @DisplayName("성공: 댓글 지우기.")
    void SUCCESS_DELETE_COMMENT_SHOULD_CHECK_IS_OK() {
        String uri = "/comments/{comment-id}";

        String commentId = TestDataRunner.COMMENT1_BY_ITEM1_MEMBER1.getCommentId().toString();
        String memberId = TestDataRunner.COMMENT1_BY_ITEM1_MEMBER1.getMember().getMemberId()
            .toString();

        params.put("comment-id", commentId);

        webTestClient.delete()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(memberId, KREAMISH_PASSWORD))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response")
            .isEmpty();
    }

    @Test
    @DisplayName("실패: 존재하지 않는 댓글 지우기")
    void FAIL_DELETE_COMMENT_SHOULD_CHECK_BAD_REQUEST() {
        String uri = "/comments/{comment-id}";

        params.put("comment-id", notExistedId);

        webTestClient.delete()
            .uri(uri, params)
            .headers(h -> h.setBasicAuth(notExistedId, KREAMISH_PASSWORD))
            .exchange()

            .expectStatus()
            .isNotFound()

            .expectBody()

            .jsonPath("$.success")
            .isNotEmpty()

            .jsonPath("$.success")
            .isEqualTo(false)

            .jsonPath("$.response")
            .isEmpty()

            .jsonPath("$.error")
            .isNotEmpty();
    }

    @Test
    @DisplayName("성공: 댓글 등록하기.")
    void SUCCESS_CREATE_COMMENT_SHOULD_CHECK_IS_OK() {
        String memberId = TestDataRunner.MEMBER1.getMemberId().toString();
        String itemId = ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2.getItemId()
            .toString();
        String content = "content3";

        Long targetItemId = ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2.getItemId();
        Long targetMemberId = TestDataRunner.MEMBER1.getMemberId();

        params.put("itemId", itemId);
        params.put("content", content);

        webTestClient.post()
            .uri("/comments")
            .headers(h -> h.setBasicAuth(memberId, "kreamish"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(params))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response")
            .isNotEmpty()

            .jsonPath("$.response.itemId")
            .isEqualTo(targetItemId)

            .jsonPath("$.response.memberId")
            .isEqualTo(targetMemberId);
    }

    @Test
    @DisplayName("성공: 특정 아이템에 달린 댓글 개수 가져오기")
    void SUCCESS_GET_COMMENT_COUNT_SHOULD_CHECK_IS_OK() {
        String uri = "/comments/count/item/{item-id}";
        String itemId = ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2.getItemId()
            .toString();
        Long targetCnt = 2L;

        params.put("item-id", itemId);

        webTestClient.get()
            .uri(uri, params)
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response.itemId")
            .isEqualTo(itemId)

            .jsonPath("$.response.commentCount")
            .isEqualTo(targetCnt);
    }

    @Test
    @DisplayName("성공: 존재하지 않는 아이템에 달린 댓글 개수 가져오기")
    void SUCCESS_GET_NOT_EXISTED_COMMENT_COUNT_SHOULD_IS_OK() {
        String uri = "/comments/count/item/{item-id}";
        Long notExistedResponse = 0L;

        params.put("item-id", String.valueOf(Integer.MAX_VALUE));

        webTestClient.get()
            .uri(uri, params)
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response.commentCount")
            .isEqualTo(notExistedResponse)

            .jsonPath("$.error")
            .isEmpty();
    }

    @Test
    @DisplayName("성공: 특정 아이템에 등록된 댓글 전체 가져오기")
    void SUCCESS_GET_ALL_COMMENT_SHOULD_IS_OK() {
        String uri = "/comments/item/{item-id}";
        String itemId = ITEM1_WITH_BRAND1_CATEGORY1_DETAIL1_AND_COMMENT_CNT_IS_2.getItemId()
            .toString();
        Long targetCommentCnt = 2L;

        params.put("item-id", itemId);

        webTestClient.get()
            .uri(uri, params)
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(true)

            .jsonPath("$.response")
            .isArray()

            .jsonPath("$.response.length()")
            .isEqualTo(targetCommentCnt);
    }

    @Test
    @DisplayName("실패: 존재하지 않는 아이템에 등록된 댓글 전체 가져오기")
    void FAIL_GET_ALL_COMMENT_ABOUT_NOT_EXISTED_ITEM_SHOULD_IS_OK() {
        String uri = "/comments/item/{item-id}";

        params.put("item-id", notExistedId);

        webTestClient.get()
            .uri(uri, params)
            .exchange()

            .expectStatus()
            .isNotFound()

            .expectBody()

            .jsonPath("$.success")
            .isEqualTo(false)

            .jsonPath("$.response")
            .isEmpty()

            .jsonPath("$.error")
            .isNotEmpty();
    }
}
