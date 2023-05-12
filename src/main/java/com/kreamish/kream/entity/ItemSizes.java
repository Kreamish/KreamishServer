package com.kreamish.kream.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class ItemSizes extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemSizesId;

    @Column(name="size")
    @Setter
    private String size;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;
}