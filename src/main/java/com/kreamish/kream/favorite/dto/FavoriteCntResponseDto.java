package com.kreamish.kream.favorite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteCntResponseDto {

    @JsonProperty("itemId")
    private final Long itemId;
    @JsonProperty("likeCnt")
    private final Long likeCnt;

    public static FavoriteCntResponseDto of(Long itemId, Long likeCnt) {
        return new FavoriteCntResponseDto(itemId, likeCnt);
    }
}
