package com.kreamish.kream.sale.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sale")
@Builder
@AllArgsConstructor
@ToString
public class Sale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_sizes_id")
    private ItemSizes itemSizes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Long salePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status", nullable = false)
    private DealStatus saleStatus;

    public static Sale of(ItemSizes itemSizes, Member member, Long salePrice,
        DealStatus saleStatus) {
        return new Sale(null, itemSizes, member, salePrice, saleStatus);
    }
}
