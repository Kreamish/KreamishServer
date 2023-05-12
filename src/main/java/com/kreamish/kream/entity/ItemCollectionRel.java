package com.kreamish.kream.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ItemCollectionRel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemCollectionRelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id",referencedColumnName = "itemId", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", referencedColumnName = "collectionId", nullable = false)
    private Collection collection;

    public static ItemCollectionRel of(Item item, Collection collection) {
        return new ItemCollectionRel(null,item, collection);
    }

}
