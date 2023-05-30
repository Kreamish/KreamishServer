package com.kreamish.kream.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class CategoryControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  @DisplayName("성공: 모든 List 반환")
  void SUCCESS_GET() {
    webTestClient.get()
        .uri("/category/all")
        .exchange();
  }

  @Test
  @DisplayName("실패: 40x 에러 발생")
  void FAIL_GET() {
  }
}