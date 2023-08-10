package com.kreamish.kream.comment.dto;

import com.kreamish.kream.comment.entity.Comment;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDto implements Serializable {

    private final Long commentId;
    private final Long itemId;
    private final Long memberId;
    private final String value;

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(comment.getCommentId(), comment.getItem().getItemId(),
            comment.getMember().getMemberId(), comment.getContent());
    }
}
