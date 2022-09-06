package com.example.restblog.response;

import com.example.restblog.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data @AllArgsConstructor
public class CreatePostResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Post post;


}
