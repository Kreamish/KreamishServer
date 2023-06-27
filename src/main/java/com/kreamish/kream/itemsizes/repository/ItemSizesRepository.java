package com.kreamish.kream.itemsizes.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.legacy.repository.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSizesRepository extends JpaRepository<ItemSizes, Long>, ItemQueryRepository {

}
