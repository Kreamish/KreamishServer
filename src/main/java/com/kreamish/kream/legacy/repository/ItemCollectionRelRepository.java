package com.kreamish.kream.legacy.repository;

import com.kreamish.kream.legacy.entity.ItemCollectionRel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCollectionRelRepository extends JpaRepository<ItemCollectionRel, Long>,
    ItemQueryRepository {

}
