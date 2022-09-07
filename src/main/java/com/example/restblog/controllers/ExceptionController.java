package com.example.restblog.controllers;

import com.example.restblog.exceptions.PostAlreadyLikedException;
import com.example.restblog.exceptions.PostNotFoundException;
import com.example.restblog.exceptions.UserNotFoundException;
import com.example.restblog.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage() , HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(exceptionResponse , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<Object> postAlreadyLikedException(PostAlreadyLikedException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage() , HttpStatus.FORBIDDEN);
        return  new ResponseEntity<>(exceptionResponse , HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> postNotFoundException(PostNotFoundException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage() , HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(exceptionResponse , HttpStatus.NOT_FOUND);
    }
}
