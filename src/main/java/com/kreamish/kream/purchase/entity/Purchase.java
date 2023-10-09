package com.kreamish.kream.purchase.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.common.entity.DealStatus;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "purchase")
@ToString
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "item_sizes_id")
    @NotNull
    private ItemSizes itemSizes;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @Column(name = "purchase_price", nullable = false)
    @NotNull
    @Min(value = 1L, message = "값은 0보다 커야 합니다.")
    private Long purchasePrice;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "purchase_status", nullable = false)
    private DealStatus purchaseStatus;

    public static Purchase of(ItemSizes itemSizes, Member member, Long purchasePrice,
        DealStatus purchaseStatus) {
        return new Purchase(null, itemSizes, member, purchasePrice, purchaseStatus);
    }
}
