package com.kreamish.kream.legacy.repository;

import com.kreamish.kream.legacy.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQueryRepository {

}