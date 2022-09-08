package com.example.restblog.repository;

import com.example.restblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentContainingIgnoreCase(String keyword);
}
