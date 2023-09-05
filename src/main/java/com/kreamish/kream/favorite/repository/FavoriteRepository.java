package com.kreamish.kream.favorite.repository;

import com.kreamish.kream.favorite.enity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
