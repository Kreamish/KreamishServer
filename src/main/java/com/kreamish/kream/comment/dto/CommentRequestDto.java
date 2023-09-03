package com.kreamish.kream.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentRequestDto {

    // ToDo : request Header에서 basic token으로 받아오게끔
    @NotNull
    private Long memberId;
    @NotNull
    private Long itemId;
    @Length(min = 1, max = 2000)
    private String content;
}
