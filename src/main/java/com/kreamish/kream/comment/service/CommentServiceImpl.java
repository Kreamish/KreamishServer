package com.kreamish.kream.comment.service;

import com.kreamish.kream.comment.dto.CommentResponseDto;
import com.kreamish.kream.comment.entity.Comment;
import com.kreamish.kream.comment.repository.CommentRepository;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.member.entity.Member;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Optional<Comment> create(Item item, Member member, String value) {
        return Optional.of(commentRepository.save(Comment.of(item, member, value)));
    }

    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public Long getCommentCount(Long itemId) {
        return commentRepository.countByItemItemId(itemId);
    }

    public List<CommentResponseDto> getComments(Long itemId) {
        return commentRepository.findAllByItemItemId(itemId).stream()
            .map(CommentResponseDto::of)
            .collect(Collectors.toList());
    }
}