package com.kreamish.kream.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ItemSizes extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemSizesId;

    @Column(name="size", nullable = false, unique = true)
    @Setter
    private String size;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    public static ItemSizes of(String size, Item item) {
        return new ItemSizes(null, size, item);
    }
}