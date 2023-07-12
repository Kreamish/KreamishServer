package com.kreamish.kream.comment.repository;


import com.kreamish.kream.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
