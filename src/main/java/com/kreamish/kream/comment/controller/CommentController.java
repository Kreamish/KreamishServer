package com.kreamish.kream.comment.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.comment.dto.CommentRequestDto;
import com.kreamish.kream.comment.dto.CommentResponseDto;
import com.kreamish.kream.comment.dto.ItemCommentCountDto;
import com.kreamish.kream.comment.facade.CommentFacade;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "댓글 등록",
        description = "유저가 아이템에 댓글 등록"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "댓글 등록 성공"),
        @ApiResponse(responseCode = "400", description = "댓글 등록 실패")
    })
    public ResponseEntity<ApiResult<CommentResponseDto>> createComment(
        @RequestBody @Valid CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(success(commentFacade.create(commentRequestDto)),
            HttpStatus.OK);
    }

    @DeleteMapping(value = "/{comment-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "댓글 삭제",
        description = "유저가 아이템에 등록한 댓글을 삭제"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "댓글 삭제 실패")
    })
    public ResponseEntity<ApiResult<?>> deleteComment(
        @PathVariable("comment-id") Long commentId,
        // ToDo : basic token header
        @RequestParam("member-id") Long memberId) {

        commentFacade.delete(commentId, memberId);
        return new ResponseEntity<>(success(null), HttpStatus.OK);
    }

    @GetMapping(value = "/count/item/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "댓글 개수 가져오기",
        description = "특정 아이템에 등록된 댓글 개수 가져오기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "댓글 개수 가져오기 성공"),
        @ApiResponse(responseCode = "400", description = "댓글 개수 가져오기 실패"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 아이템")
    })
    public ResponseEntity<ApiResult<ItemCommentCountDto>> getCommentCount(
        @PathVariable("item-id") Long itemId) {
        Long commentCount = commentFacade.getCommentCount(itemId);
        ItemCommentCountDto itemCommentCountDto = ItemCommentCountDto.of(itemId, commentCount);

        return new ResponseEntity<>(success(itemCommentCountDto), HttpStatus.OK);
    }

    @GetMapping(value = "/item/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "댓글 전체 가져오기",
        description = "특정 아이템에 등록된 댓글 전체 댓글 가져오기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "댓글 전체 가져오기 성공"),
        @ApiResponse(responseCode = "400", description = "댓글 전체 가져오기 실패"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 아이템")
    })
    public ResponseEntity<ApiResult<List<CommentResponseDto>>> getComments(
        @PathVariable("item-id") Long itemId) {
        List<CommentResponseDto> commentsResponseDtoList = commentFacade.getComments(itemId);

        return new ResponseEntity<>(success(commentsResponseDtoList), HttpStatus.OK);
    }
}