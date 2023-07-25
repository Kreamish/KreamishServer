package com.kreamish.kream.comment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemCommentCountDto {

    private Long itemId;
    private Long commentCount;

    public static ItemCommentCountDto of(Long itemId, Long commentCount) {
        return new ItemCommentCountDto(itemId, commentCount);
    }
}
