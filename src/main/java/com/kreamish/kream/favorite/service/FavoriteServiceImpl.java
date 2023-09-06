package com.kreamish.kream.favorite.service;

import com.kreamish.kream.favorite.dto.FavoriteResponseDto;
import com.kreamish.kream.favorite.dto.ItemFavoriteResponseDto;
import com.kreamish.kream.favorite.dto.MemberFavoriteListResponseDto;
import com.kreamish.kream.favorite.enity.Favorite;
import com.kreamish.kream.favorite.repository.FavoriteRepository;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.service.ItemSizeService;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.service.MemberService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private static final Long ZERO_CNT = 0L;
    private final FavoriteRepository favoriteRepository;
    private final ItemSizeService itemSizeService;
    private final MemberService memberService;

    @Transactional
    public FavoriteResponseDto createFavorite(Long itemSizesId, Long memberId) {
        ItemSizes itemSizes = itemSizeService.findById(itemSizesId)
            .orElseThrow(() -> new NoSuchElementException("Item Sizes Not Found"));
        Member member = memberService.getMemberById(memberId)
            .orElseThrow(() -> new NoSuchElementException("Member Not Found"));

        Favorite saveFavorite = favoriteRepository.save(Favorite.of(itemSizes, member));

        return FavoriteResponseDto.of(saveFavorite);
    }

    @Transactional
    public void deleteFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
            .orElseThrow(() -> new NoSuchElementException("Favorite Not Found"));

        favoriteRepository.deleteById(favoriteId);
    }

    public Long getFavoriteCnt(Long itemId) {
        return favoriteRepository.getFavoriteCnt(itemId);
    }

    @Override
    public Long register(Long memberId, Long itemSizesId) {
        return null;
    }

    @Override
    public MemberFavoriteListResponseDto inQueryByMemberId(Long memberId) {
        return null;
    }

    @Override
    public ItemFavoriteResponseDto inQueryByItemId(Long itemId) {
        return null;
    }

    @Override
    public void remove(Long favoriteId) {

    }
}
