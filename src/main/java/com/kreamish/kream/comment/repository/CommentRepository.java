package com.kreamish.kream.comment.repository;


import com.kreamish.kream.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByItemItemId(Long itemId);

    List<Comment> findAllByItemItemId(Long itemId);
}
