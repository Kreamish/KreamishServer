package com.kreamish.kream.comment.service;

import com.kreamish.kream.comment.dto.CommentResponseDto;
import com.kreamish.kream.comment.entity.Comment;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.member.entity.Member;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> create(Item item, Member member, String value);

    Optional<Comment> findById(Long commentId);

    void delete(Comment comment);

    Long getCommentCount(Long itemId);

    List<CommentResponseDto> getComments(Long itemId);
}
