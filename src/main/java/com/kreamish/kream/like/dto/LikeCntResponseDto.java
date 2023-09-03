package com.kreamish.kream.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeCntResponseDto {

    @JsonProperty("itemId")
    private final Long itemId;
    @JsonProperty("likeCnt")
    private final Long likeCnt;

    public static LikeCntResponseDto of(Long itemId, Long likeCnt) {
        return new LikeCntResponseDto(itemId, likeCnt);
    }
}
