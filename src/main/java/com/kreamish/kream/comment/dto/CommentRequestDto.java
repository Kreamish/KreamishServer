package com.kreamish.kream.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentRequestDto {

    @NotNull
    @JsonProperty(value = "member_id")
    private Long memberId;
    @NotNull
    @JsonProperty(value = "item_id")
    private Long itemId;
    @Length(min = 1, max = 2000)
    private String content;
}
