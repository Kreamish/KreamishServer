package com.kreamish.kream.comment.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.legacy.entity.Item;
import com.kreamish.kream.legacy.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "item_Id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @Length(min = 1, max = 2000)
    private String value;
}
