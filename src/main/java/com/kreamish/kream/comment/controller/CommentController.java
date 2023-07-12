package com.kreamish.kream.comment.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.comment.dto.CommentRequestDto;
import com.kreamish.kream.comment.dto.CommentResponseDto;
import com.kreamish.kream.comment.facade.CommentFacade;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping
    @Operation(
        summary = "댓글 등록",
        description = "유저가 아이템에 댓글 등록"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 등록")
    })
    public ResponseEntity<ApiResult<CommentResponseDto>> createComment(
        @RequestBody @Valid CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(success(commentFacade.create(commentRequestDto)),
            HttpStatus.OK);
    }

}
