package com.kreamish.kream.like.facade;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.itemsizes.service.ItemSizeService;
import com.kreamish.kream.like.dto.LikeResponseDto;
import com.kreamish.kream.like.dto.LikedItemSizesResponseDto;
import com.kreamish.kream.like.entity.Like;
import com.kreamish.kream.like.servie.LikeService;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.service.MemberService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class LikeFacade {

    private static final Long ZERO_CNT = 0L;
    private final LikeService likeService;
    private final ItemSizeService itemSizeService;
    private final MemberService memberService;

    @Transactional
    public LikeResponseDto createLike(Long itemSizesId, Long memberId) {
        ItemSizes itemSizes = itemSizeService.findById(itemSizesId)
            .orElseThrow(() -> new NoSuchElementException("Item Sizes Not Found"));
        Member member = memberService.getMemberById(memberId)
            .orElseThrow(() -> new NoSuchElementException("Member Not Found"));

        Like saveLike = likeService.save(Like.of(itemSizes, member));

        return LikeResponseDto.of(saveLike);
    }

    @Transactional
    public void deleteLike(Long likeId) {
        Like like = likeService.findById(likeId)
            .orElseThrow(() -> new NoSuchElementException("Like Not Found"));

        likeService.deleteBy(like);
    }

    public Long getLikeCnt(Long itemId) {
        return likeService.getLikeCnt(itemId);
    }

    public LikedItemSizesResponseDto getLikedItemSizes(Long itemId, Long memberId) {
        return LikedItemSizesResponseDto.of(likeService.getLikedItemSizes(itemId, memberId),
            itemId);
    }
}
