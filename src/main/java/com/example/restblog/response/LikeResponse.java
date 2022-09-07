package com.example.restblog.response;

import com.example.restblog.models.Like;
import com.example.restblog.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class LikeResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Post post;
    private Like like;
    private int totalLike;


}
