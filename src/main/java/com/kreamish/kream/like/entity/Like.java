package com.kreamish.kream.like.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.itemsizes.entity.ItemSizes;
import com.kreamish.kream.member.entity.Member;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "likes")
@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "item_sizes_id")
    private ItemSizes itemSizes;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    public static Like of(ItemSizes itemSizes, Member member) {
        return new Like(null, itemSizes, member);
    }
}
