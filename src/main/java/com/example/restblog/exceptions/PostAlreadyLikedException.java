package com.example.restblog.exceptions;

import lombok.Getter;

@Getter
public class PostAlreadyLikedException extends RuntimeException{
    private final String message;


    public PostAlreadyLikedException(String message) {
        this.message = message;
    }
}
