package com.kreamish.kream.itemsizes.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.legacy.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ItemSizes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemSizesId;

    @Column(name = "size", nullable = false, unique = false)
    @Setter
    private String size;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    public static ItemSizes of(String size, Item item) {
        return new ItemSizes(null, size, item);
    }
}