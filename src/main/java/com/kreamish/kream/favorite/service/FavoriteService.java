package com.kreamish.kream.favorite.service;

import com.kreamish.kream.favorite.dto.FavoriteItemsReponseDto;
import com.kreamish.kream.favorite.dto.FavoriteResponseDto;
import com.kreamish.kream.favorite.dto.ItemFavoriteResponseDto;
import com.kreamish.kream.favorite.dto.MemberFavoriteListResponseDto;

public interface FavoriteService {

    Long register(Long memberId, Long itemSizesId);

    MemberFavoriteListResponseDto inQueryByMemberId(Long memberId);

    ItemFavoriteResponseDto inQueryByItemId(Long itemId);

    void remove(Long favoriteId);

    Long getFavoriteCnt(Long itemId);

    void deleteFavorite(Long favoriteId);

    FavoriteResponseDto createFavorite(Long itemSizesId, Long memberId);

    FavoriteItemsReponseDto getFavorite(long l, String s);
}
