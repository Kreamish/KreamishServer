package com.kreamish.kream.favorite.controller;

import static com.kreamish.kream.common.util.ApiUtils.success;

import com.kreamish.kream.common.util.ApiUtils.ApiResult;
import com.kreamish.kream.favorite.dto.FavoriteCntResponseDto;
import com.kreamish.kream.favorite.dto.FavoriteResponseDto;
import com.kreamish.kream.favorite.service.FavoriteService;
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
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping(value = "/item-sizes-id/{item-sizes-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "관심상품 표시",
        description = "특정 아이템의 아이템 사이즈에 관심상품 표시하기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "관심상품 표시 성공"),
        @ApiResponse(responseCode = "400", description = "필수 파라미터가 전달되지 않음"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 id 값 전달")
    })
    ResponseEntity<ApiResult<FavoriteResponseDto>> createFavorite(
        @PathVariable("item-sizes-id") Long itemSizesId,
        @Login LoginMemberInfo loginMemberInfo) {
        FavoriteResponseDto favoriteResponseDto = favoriteService.createFavorite(itemSizesId,
            loginMemberInfo.getMemberId());

        return new ResponseEntity<>(success(favoriteResponseDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{favorite-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "관심상품 취소",
        description = "특정 아이템에 관심상품 취소하기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "관심상품 취소하기 성공"),
        @ApiResponse(responseCode = "400", description = "관심상품 id가 전달되지 않음"),
        @ApiResponse(responseCode = "404", description = "취소할 관심상품가 없음")
    })
    ResponseEntity<ApiResult<?>> deleteFavorite(
        @PathVariable("favorite-id") @Valid @NotNull Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);

        return new ResponseEntity<>(success(null), HttpStatus.OK);
    }

    @GetMapping(value = "/count/item/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "관심상품 개수 가져오기",
        description = "특정 아이템에 표시된 관심상품 총 개수를 가져오기"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "관심상품 개수 가져오기 성공"),
        @ApiResponse(responseCode = "400", description = "관심상품 id가 올바르지 않거나 전달되지 않았다.")
    })
    ResponseEntity<ApiResult<?>> getFavoriteCnt(
        @PathVariable("item-id") @Valid @NotNull Long itemId) {
        Long cnt = favoriteService.getFavoriteCnt(itemId);

        return new ResponseEntity<>(success(FavoriteCntResponseDto.of(itemId, cnt)),
            HttpStatus.OK);
    }
}
