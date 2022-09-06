package com.example.restblog.response;

import com.example.restblog.models.Comment;
import com.example.restblog.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class CommentResponse {
    private String message;
    private Comment comment;
    private LocalDateTime timeStamp;
    private Post post;
}
