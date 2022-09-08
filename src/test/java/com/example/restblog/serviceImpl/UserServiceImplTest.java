package com.example.restblog.serviceImpl;

import com.example.restblog.dto.*;
import com.example.restblog.models.Comment;
import com.example.restblog.models.Like;
import com.example.restblog.models.Post;
import com.example.restblog.models.User;
import com.example.restblog.repository.CommentRepository;
import com.example.restblog.repository.LikeRepository;
import com.example.restblog.repository.PostRepository;
import com.example.restblog.repository.UserRepository;
import com.example.restblog.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Calendar.SEPTEMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private LocalDateTime localDateTime;
    private User user;
    private Comment comment;
    private Like like;
    private Post post;
    List<Like> likeList = new ArrayList<>();
    List<Post> postList = new ArrayList<>();
    List<Comment> commentList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.of(2022, SEPTEMBER,3,6,30,40,50000);
        user = new User(1L , "Chioma" , "chioma@gmail.com" , "user" , "1234" , localDateTime , localDateTime  , postList , commentList , likeList);
        post = new Post(1L , "bible  plan" , "nicky and pippa" , "bible-plan" , "oxy.png", localDateTime , localDateTime , user, commentList , likeList);
        like = new Like(1L , true , localDateTime , localDateTime , post , user);
        comment = new Comment(1L , "I like" , localDateTime , localDateTime , post , user);


    }

    @Test
    void findUserByEmail() {
        when(userRepository.findUserByEmail("chioma@gmail.com")).thenReturn(Optional.ofNullable(user));
        assertEquals(user , userService.findUserByEmail("chioma@gmail.com"));

    }

    @Test
    void findUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        assertEquals(user , userService.findUserById(1L));
    }


    @Test
    void findPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
        assertEquals(post , userService.findPostById(1L));
    }

    @Test
    void createSlug() {
    }

    @Test
    void register() {
        UserDto userDTO = new UserDto( "Chioma" , "chioma@gmail.com" , "user" , "1234");
        when(userRepository.save(user)).thenReturn(user);
        RegisterResponse registerResponse = new RegisterResponse("success" , localDateTime , user);
        var actual =  userService.register(userDTO);
        actual.setTimeStamp(localDateTime);
        actual.getUser().setCreatedAt(localDateTime);
        actual.getUser().setUpdatedAt(localDateTime);
        actual.getUser().setId(1L);
        assertEquals(registerResponse, actual);
    }

    @Test
    void loginUser() {
        LoginDto loginDto = new LoginDto("chioma@gmail.com", "1234");
        when(userRepository.findUserByEmail("chioma@gmail.com")).thenReturn(Optional.ofNullable(user));
        LoginResponse loginResponse = new LoginResponse("success" , localDateTime);
        var actual =  userService.loginUser(loginDto);
        actual.setTimeStamp(localDateTime);
        assertEquals(loginResponse , actual);
    }
//
    @Test
    void login_IncorrectPassword() {
        LoginDto loginDto = new LoginDto("chioma@gmail.com", "1238745");
        when(userRepository.findUserByEmail("chioma@gmail.com")).thenReturn(Optional.ofNullable(user));
        LoginResponse loginResponse = new LoginResponse("Password MisMatch", localDateTime);
        var actual = userService.loginUser(loginDto);
        actual.setTimeStamp(localDateTime);
        assertEquals(loginResponse, actual);
    }

    @Test
    void createPost() {

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        PostDto postDto = new PostDto("bible  plan" , "nicky and pippa" ,  "oxy.png",1L);
        CreatePostResponse createPostResponse = new CreatePostResponse("success" ,localDateTime,post);
        var actual = userService.createPost(postDto);
        actual.setTimeStamp(localDateTime);
        actual.getPost().setCreatedAt(localDateTime);
        actual.getPost().setUpdatedAt(localDateTime);
        actual.getPost().setId(1L);
        actual.getPost().setSlug("bible-plan");
        assertEquals(createPostResponse,actual);
    }

    @Test
    void createComment() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
        CommentDto commentDto = new CommentDto("I like");
        CommentResponse commentResponse = new CommentResponse("success"  , comment, localDateTime , post);
        var actual = userService.createComment(1L , 1L  , commentDto);
        actual.setTimeStamp(localDateTime);
        actual.setComment(comment);
        commentResponse.setComment(comment);
        commentResponse.setPost(post);
        assertEquals(commentResponse , actual);

    }

    @Test
    void like() {

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
        List<Like> likes = new ArrayList<>(Arrays.asList(like));
        when(likeRepository.likeList(1)).thenReturn(likes);
        LikeDto likeDto = new LikeDto(true);
        LikeResponse likeResponse = new LikeResponse("success" , localDateTime , post, like , 1);
        var actual = userService.like(1L , 1L  , likeDto);
        actual.setTimeStamp(localDateTime);
        actual.setLike(like);
        likeResponse.getLike().setUser(user);
        likeResponse.getLike().setPost(post);
        assertEquals(likeResponse , actual);

    }

    @Test
    void searchComment() {

    }

    @Test
    void searchPost() {
    }
}