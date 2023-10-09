package com.kreamish.kream.comment.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Table(name = "comment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_Id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @Length(min = 1, max = 2000)
    private String content;

    public static Comment of(Item item, Member member, String value) {
        return new Comment(null, item, member, value);
    }

    public boolean isBelongTo(Long memberId) {
        return this.getMember().getMemberId().equals(memberId);
    }
}
