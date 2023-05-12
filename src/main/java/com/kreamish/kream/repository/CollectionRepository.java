package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Collection;
import com.kreamish.kream.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long>, ItemQueryRepository {
}
