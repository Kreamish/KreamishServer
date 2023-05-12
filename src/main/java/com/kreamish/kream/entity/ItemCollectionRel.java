package com.kreamish.kream.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ItemCollectionRel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemCollectionRelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id",referencedColumnName = "itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", referencedColumnName = "collectionId")
    private Collection collection;
}
