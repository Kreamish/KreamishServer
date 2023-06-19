package com.kreamish.kream.collection.repository;

import com.kreamish.kream.collection.entity.Collection;
import com.kreamish.kream.legacy.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long>, ItemQueryRepository {

}
