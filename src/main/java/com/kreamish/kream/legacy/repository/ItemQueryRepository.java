package com.kreamish.kream.legacy.repository;

import com.kreamish.kream.legacy.entity.Item;
import java.util.List;

public interface ItemQueryRepository {

    List<Item> findItemsWhereLikes(String name);
}
