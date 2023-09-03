package com.kreamish.kream.like.servie;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.like.entity.Like;
import com.kreamish.kream.like.repository.LikeRepository;
import com.kreamish.kream.member.entity.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    public Optional<Like> findBy(ItemSizes itemSizes, Member member) {
        return likeRepository.findByItemSizesAndMember(itemSizes, member);
    }

    @Transactional
    public void deleteBy(Like like) {
        likeRepository.delete(like);
    }

    public Optional<Like> findById(Long likeId) {
        return likeRepository.findById(likeId);
    }

    public Long getLikeCnt(Long itemId) {
        return likeRepository.getLikeCnt(itemId);
    }

    public List<ItemSizes> getLikedItemSizes(Long itemId, Long memberId) {
        return likeRepository.getLikedItemSizes(itemId, memberId);
    }
}
