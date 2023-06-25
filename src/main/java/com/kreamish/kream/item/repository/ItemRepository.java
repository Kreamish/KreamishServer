package com.kreamish.kream.item.repository;

import com.kreamish.kream.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemQueryRepository {

}
