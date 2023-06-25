package com.kreamish.kream.item.repository;


import static com.kreamish.kream.legacy.entity.QItem.item;

import com.kreamish.kream.legacy.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory query;


    public List<Item> findItemsWhereLikes(String name) {
        return query.select(item)
            .from(item)
            .where(item.name.like(name))
            .fetch();
    }
}
