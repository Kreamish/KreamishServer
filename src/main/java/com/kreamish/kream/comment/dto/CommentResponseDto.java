package com.kreamish.kream.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("item_id")
    private Long itemId;
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("value")
    private String value;

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(comment.getCommentId(), comment.getItem().getItemId(),
            comment.getMember().getMemberId(), comment.getContent());
    }
}
