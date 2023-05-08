package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQueryRepository {
}
