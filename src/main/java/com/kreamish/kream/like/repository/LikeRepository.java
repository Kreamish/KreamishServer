package com.kreamish.kream.like.repository;

import com.kreamish.kream.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
