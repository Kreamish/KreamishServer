package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Item;

import java.util.List;

public interface ItemQueryRepository {
    List<Item> findItemsWhereLikes(String name);
}
