package com.kreamish.kream.legacy.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "item_sizes_id")
    private ItemSizes itemSizes;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "purchase_price", nullable = false)
    private Long purchasePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_status", nullable = false)
    @Length(max = 100)
    private DealStatus purchaseStatus;
}
