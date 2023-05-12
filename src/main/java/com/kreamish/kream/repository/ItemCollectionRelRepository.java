package com.kreamish.kream.repository;

import com.kreamish.kream.entity.ItemCollectionRel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCollectionRelRepository extends JpaRepository<ItemCollectionRel, Long>, ItemQueryRepository {
}
