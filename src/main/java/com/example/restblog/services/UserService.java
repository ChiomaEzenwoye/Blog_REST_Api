package com.example.restblog.services;

import com.example.restblog.dto.*;
import com.example.restblog.models.Post;
import com.example.restblog.models.User;
import com.example.restblog.response.*;
import org.springframework.stereotype.Service;

public interface UserService {
    User findUserByEmail(String email);

    User findUserById(long id);

    Post findPostById(long id);

    String createSlug(String input);

    RegisterResponse register(UserDto userDto);
    LoginResponse loginUser(LoginDto loginDto);
    CreatePostResponse createPost(PostDto postDto);
    CommentResponse createComment( long user_id, long post_id, CommentDto commentDto);
    LikeResponse like( long user_id, long post_id, LikeDto likeDto);
    SearchCommentResponse searchComment(String keyWord);
    SearchPostResponse searchPost(String keyWord);



}
