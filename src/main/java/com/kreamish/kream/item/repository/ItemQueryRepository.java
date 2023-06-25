package com.kreamish.kream.item.repository;

import com.kreamish.kream.item.dto.ItemListSearchCondition;
import com.kreamish.kream.item.entity.Item;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryRepository {

    List<Item> findItemsWhereLikes(String name);

    Page<Item> findItemsByCondition(ItemListSearchCondition condition, Pageable pageable);
}
