package com.kreamish.kream.item.entity;

import com.kreamish.kream.common.entity.BaseEntity;
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
import lombok.Builder;
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
@Builder
public class ItemSizes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_sizes_id")
    private Long itemSizesId;

    @Column(name = "size", nullable = false, unique = true)
    @Setter
    private String size;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public static ItemSizes of(String size, Item item) {
        return new ItemSizes(null, size, item);
    }
}