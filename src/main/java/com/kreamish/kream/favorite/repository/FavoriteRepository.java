package com.kreamish.kream.favorite.repository;

import com.kreamish.kream.favorite.enity.Favorite;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteQueryRepository {

    Optional<Favorite> findByItemSizesAndMember(ItemSizes itemSizes, Member member);
}
