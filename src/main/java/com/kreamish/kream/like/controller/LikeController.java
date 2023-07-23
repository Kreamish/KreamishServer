package com.kreamish.kream.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    @PostMapping
    @Operation(
        summary = "좋아요 표시",
        description = "특정 아이템에 좋아요 표시하기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 표시 성공"),
        @ApiResponse(responseCode = "400", description = "좋아요 표시 실패")
    })
    void createLike() {

    }

    @DeleteMapping
    @Operation(
        summary = "좋아요 취소하기",
        description = "특정 아이템에 좋아요 취소하기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 취소하기 성공"),
        @ApiResponse(responseCode = "400", description = "좋아요 취소하기 실패")
    })
    void deleteLike() {

    }

    @GetMapping("/count/item/{item-id}")
    @Operation(
        summary = "좋아요 개수 가져오기",
        description = "특정 아이템에 표시된 좋아요 총 개수를 가져오기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 개수 가져오기 성공"),
        @ApiResponse(responseCode = "400", description = "좋아요 개수 가져오기 실패")
    })
    void getLikeCnt(@PathVariable("item-id") Long itemId) {

    }
}
