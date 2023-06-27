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
import jakarta.persistence.Table;
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
@Table(name = "item_sizes")
public class ItemSizes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_sizes_id")
    private Long itemSizesId;

    @Column(name = "size", nullable = false, unique = false)
    @Setter
    private String size;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public static ItemSizes of(String size, Item item) {
        return new ItemSizes(null, size, item);
    }
}