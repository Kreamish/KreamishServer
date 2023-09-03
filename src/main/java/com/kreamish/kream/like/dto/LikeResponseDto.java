package com.kreamish.kream.like.dto;

import com.kreamish.kream.like.entity.Like;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeResponseDto {

    private final Long likeId;

    public static LikeResponseDto of(Like like) {
        return new LikeResponseDto(like.getLikeId());
    }
}
