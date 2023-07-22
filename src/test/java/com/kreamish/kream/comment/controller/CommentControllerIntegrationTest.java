package com.kreamish.kream.comment.controller;

import com.kreamish.kream.comment.repository.CommentRepository;
import com.kreamish.kream.common.runner.TestDataRunner;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "data"})
@Transactional
public class CommentsControllerIntegrationTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    WebTestClient webTestClient;


    @Test
    void SUCCESS_CREATE_COMMENT() {
        Map params = new HashMap();

        params.put("member-id", TestDataRunner.member1.getMemberId());
        params.put("item-id", TestDataRunner.item1WithBrand1AndCategory1AndDetail1.getItemId());
        params.put("value", "content3");

        webTestClient.post()
            .body(BodyInserters.fromValue(params))
            .exchange()

            .expectStatus()
            .isOk()

            .expectBody()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isNotEmpty()
            .jsonPath("$.response.item_id")
            .isEqualTo(TestDataRunner.item1WithBrand1AndCategory1AndDetail1.getItemId())
            .jsonPath("$.response.member_id").isEqualTo(TestDataRunner.member1.getMemberId());
    }

    @Test
    void deleteComment() {
    }

    @Test
    void getComment() {

    }

    @Test
    void getComments() {
    }
}
