package com.kreamish.kream.itemsizes.repository;

import com.kreamish.kream.legacy.entity.Item;
import com.kreamish.kream.legacy.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQueryRepository {

}
