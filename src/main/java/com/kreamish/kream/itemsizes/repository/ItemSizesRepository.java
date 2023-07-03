package com.kreamish.kream.itemsizes.repository;

import com.kreamish.kream.itemsizes.entity.ItemSizes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemSizesRepository extends JpaRepository<ItemSizes, Long> {

    @Query("select s from ItemSizes s join fetch s.item i "
        + "where i.itemId = :itemId")
    List<ItemSizes> findByItemId(Long itemId);
}
