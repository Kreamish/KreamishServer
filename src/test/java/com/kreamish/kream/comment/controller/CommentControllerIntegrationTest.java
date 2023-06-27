package com.kreamish.kream.comment.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class CommentControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void SET_UP() {
        String dummyStr = "dummy";


    }

    @Test
    void SUCCESS_CREATE_COMMENT() {

    }

    @Test
    void deleteComment() {
    }

    @Test
    void getCommentCount() {
    }

    @Test
    void getComments() {
    }
}
