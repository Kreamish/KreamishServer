package com.kreamish.kream.repository;

import com.kreamish.kream.entity.ItemSizes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSizesRepository extends JpaRepository<ItemSizes, Long>, ItemQueryRepository {

}
