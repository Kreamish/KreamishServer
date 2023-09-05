package com.kreamish.kream.favorite.service;

import com.kreamish.kream.favorite.dto.ItemFavoriteResponseDto;
import com.kreamish.kream.favorite.dto.MemberFavoriteListResponseDto;
import java.util.List;

public interface FavoriteService {

    Long register(Long memberId, Long itemSizesId);

    MemberFavoriteListResponseDto inQueryByMemberId(Long memberId);

    ItemFavoriteResponseDto inQueryByItemId(Long itemId);

    void remove(Long favoriteId);
}
