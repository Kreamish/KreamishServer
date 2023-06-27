package com.kreamish.kream.collection.dto;

import com.kreamish.kream.collection.entity.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CollectionDto {

    private final Long id;
    private final String name;

    public static CollectionDto of(Collection collection) {
        return new CollectionDto(collection.getCollectionId(), collection.getName());
    }
}
