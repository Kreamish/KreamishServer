package com.kreamish.kream.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentRequestDto {

    @NotNull
    private Long itemId;
    @Length(min = 1, max = 2000)
    private String content;
}
