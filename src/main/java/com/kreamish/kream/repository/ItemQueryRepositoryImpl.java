package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Item;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory query;


    public List<Item> findItemsWhereLikes(String name){
        return null;

    }
}
