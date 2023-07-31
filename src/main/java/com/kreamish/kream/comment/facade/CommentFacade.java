package com.kreamish.kream.comment.facade;

import com.kreamish.kream.comment.dto.CommentRequestDto;
import com.kreamish.kream.comment.dto.CommentResponseDto;
import com.kreamish.kream.comment.entity.Comment;
import com.kreamish.kream.comment.service.CommentService;
import com.kreamish.kream.item.entity.Item;
import com.kreamish.kream.item.service.ItemService;
import com.kreamish.kream.member.entity.Member;
import com.kreamish.kream.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class CommentFacade {

    private final ItemService itemService;
    private final MemberService memberService;
    private final CommentService commentService;

    @Transactional
    public CommentResponseDto create(CommentRequestDto commentRequestDto) {
        Member member = memberService.getMemberById(commentRequestDto.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        Item item = itemService.getItemById(commentRequestDto.getItemId())
            .orElseThrow(() -> new IllegalArgumentException("Item Not Found"));

        return commentService.create(item, member, commentRequestDto.getContent())
            .map(CommentResponseDto::of)
            .orElseThrow(() -> new IllegalArgumentException("JPA Save Exception"));
    }

    @Transactional
    public void delete(Long commentId, Long memberId) {
        Comment comment = commentService.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment Not Found"));

        if (!comment.isBelongTo(memberId)) {
            throw new IllegalArgumentException("Comment that does not belong to the Member");
        }

        commentService.delete(comment);
    }


    public Long getCommentCount(Long itemId) {
        return commentService.getCommentCount(itemId);
    }

    public List<CommentResponseDto> getComments(Long itemId) {
        return commentService.getComments(itemId);
    }
}