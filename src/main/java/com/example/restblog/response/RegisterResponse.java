package com.example.restblog.response;

import com.example.restblog.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor

public class RegisterResponse {
    private String message;
    private LocalDateTime timeStamp;
    private User user;
}
