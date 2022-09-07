package com.example.restblog.controllers;

import com.example.restblog.dto.CommentDto;
import com.example.restblog.dto.LikeDto;
import com.example.restblog.dto.PostDto;
import com.example.restblog.dto.UserDto;
import com.example.restblog.models.Post;
import com.example.restblog.response.*;
import com.example.restblog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class UserControllers {
    private final UserService userService;

    @Autowired
    public UserControllers(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(value  ="/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody UserDto userDto){
        log.info("Successfully Registered {} ", userDto.getEmail());
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register").toUriString());
        return new ResponseEntity<>(userService.register(userDto),CREATED);

    }

    @PostMapping(value = "/createPost")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostDto postDto){
        log.info("Successfully Created A post With Title:  {} " , postDto.getTitle());
        return new ResponseEntity<>(userService.createPost(postDto), CREATED);
    }

    @PostMapping(value = "/comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable (value = "user_id") Long user_id, @PathVariable(value ="post_id") Long post_id, @RequestBody CommentDto commentDto){
        log.info("Successfully commented :  {} " , commentDto.getComment());
        return new ResponseEntity<>(userService.createComment(user_id, post_id, commentDto), CREATED);

    }
    @PostMapping(value = "/like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable (value = "user_id") Long user_id, @PathVariable(value ="post_id") Long post_id, @RequestBody LikeDto likeDto){
        log.info("Successfully commented :  {} " , likeDto.isLiked());
        return new ResponseEntity<>(userService.like(user_id, post_id, likeDto), OK);

    }

    @GetMapping(value ="/searchPost{keyword}")
    public ResponseEntity<SearchPostResponse> searchPost(@PathVariable(value="keyword") String keyword){
        return new ResponseEntity<>(userService.searchPost(keyword), FOUND);
    }

    @GetMapping(value ="/searchComment{keyword}")
    public ResponseEntity<SearchCommentResponse> searchComment(@PathVariable(value="keyword") String keyword){
        return new ResponseEntity<>(userService.searchComment(keyword), FOUND);
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Post> searchComment(@PathVariable(  value = "id") Integer id){
        return ResponseEntity.ok().body(userService.findPostById(id));
    }










}
