package com.kreamish.kream.like.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.like.entity.Like;
import com.kreamish.kream.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeQueryRepository {

    Optional<Like> findByItemSizesAndMember(ItemSizes itemSizes, Member member);
}
