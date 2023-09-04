package com.kreamish.kream.like.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils;
import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.like.dto.LikeCntResponseDto;
import com.kreamish.kream.like.dto.LikeResponseDto;
import com.kreamish.kream.like.dto.LikedItemSizesResponseDto;
import com.kreamish.kream.like.facade.LikeFacade;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeFacade likeFacade;

    @GetMapping(value = "/item/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "좋아요 표시한 아이템 사이즈 리스트 가져오기",
        description = "특정 유저가 특정 아이템의 아이템 사이즈들을 좋아요 했을 때 좋아요 했던 리스트 가져오기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 표시한 아이템 사이즈 리스트 가져오기 성공"),
        @ApiResponse(responseCode = "400", description = "필수 파라미터가 전달되지 않았거나 잘못 전달 됨")
    })
    ResponseEntity<ApiResult<LikedItemSizesResponseDto>> getLikedItemSizes(
        @PathVariable("item-id") @Valid @NotNull Long itemId,
        @Login LoginMemberInfo loginMemberInfo) {
        LikedItemSizesResponseDto likedItemSizesResponseDto = likeFacade.getLikedItemSizes(itemId,
            loginMemberInfo.getMemberId());

        return new ResponseEntity<>(ApiUtils.success(likedItemSizesResponseDto), HttpStatus.OK);
    }

    @PostMapping(value = "/item-sizes-id/{item-sizes-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "좋아요 표시",
        description = "특정 아이템의 아이템 사이즈에 좋아요 표시하기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 표시 성공"),
        @ApiResponse(responseCode = "400", description = "필수 파라미터가 전달되지 않음"),
        @ApiResponse(responseCode = "404", description = "잘못된 파라미터")
    })
    ResponseEntity<ApiResult<LikeResponseDto>> createLike(
        @PathVariable("item-sizes-id") Long itemSizesId,
        @Login LoginMemberInfo loginMemberInfo) {
        Long memberId = loginMemberInfo.getMemberId();

        LikeResponseDto likeResponseDto = likeFacade.createLike(itemSizesId, memberId);

        return new ResponseEntity<>(success(likeResponseDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{like-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "좋아요 취소",
        description = "특정 아이템에 좋아요 취소하기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 취소하기 성공"),
        @ApiResponse(responseCode = "400", description = "좋아요 id가 전달되지 않음"),
        @ApiResponse(responseCode = "404", description = "취소할 좋아요가 없음")
    })
    ResponseEntity<ApiResult<?>> deleteLike(@PathVariable("like-id") @Valid @NotNull Long likeId) {
        likeFacade.deleteLike(likeId);

        return new ResponseEntity<>(success(null), HttpStatus.OK);
    }

    @GetMapping(value = "/count/item/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "좋아요 개수 가져오기",
        description = "특정 아이템에 표시된 좋아요 총 개수를 가져오기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 개수 가져오기 성공"),
        @ApiResponse(responseCode = "400", description = "좋아요 id가 전달되지 않음")
    })
    ResponseEntity<ApiResult<?>> getLikeCnt(@PathVariable("item-id") @Valid @NotNull Long itemId) {
        Long cnt = likeFacade.getLikeCnt(itemId);

        return new ResponseEntity<>(success(LikeCntResponseDto.of(itemId, cnt)),
            HttpStatus.OK);
    }
}
