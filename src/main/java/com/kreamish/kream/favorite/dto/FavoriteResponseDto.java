package com.kreamish.kream.favorite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kreamish.kream.favorite.enity.Favorite;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FavoriteResponseDto {

    @JsonProperty
    private final Long favoriteId;

    @JsonProperty
    private final Long itemSizesId;

    public static FavoriteResponseDto of(Favorite favorite) {
        return new FavoriteResponseDto(favorite.getFavoriteId(),
            favorite.getItemSizes().getItemSizesId());
    }
}
