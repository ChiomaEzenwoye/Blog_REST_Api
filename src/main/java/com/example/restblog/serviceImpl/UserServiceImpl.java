package com.example.restblog.serviceImpl;

import com.example.restblog.dto.*;
import com.example.restblog.exceptions.PostAlreadyLikedException;
import com.example.restblog.exceptions.PostNotFoundException;
import com.example.restblog.exceptions.UserNotFoundException;
import com.example.restblog.models.Comment;
import com.example.restblog.models.Like;
import com.example.restblog.models.Post;
import com.example.restblog.models.User;
import com.example.restblog.repository.CommentRepository;
import com.example.restblog.repository.LikeRepository;
import com.example.restblog.repository.PostRepository;
import com.example.restblog.repository.UserRepository;
import com.example.restblog.response.*;
import com.example.restblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private  final CommentRepository commentRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, LikeRepository likeRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }



    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    @Override
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
    }
    @Override
    public User findUserById(long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found in DB"));
    }
    @Override
    public Post findPostById(long id){
        return postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post not found in DB"));
    }
    @Override
    public String createSlug(String input) {
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }


    @Override
    public RegisterResponse register(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        userRepository.save(user);
        return new RegisterResponse("success", LocalDateTime.now(), user);
    }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        User loggedInUser = findUserByEmail(loginDto.getEmail());
        LoginResponse loginResponse = null;
        if(loggedInUser != null){
            if(loggedInUser.getPassword().equals(loginDto.getPassword())){
                loginResponse = new LoginResponse("success", LocalDateTime.now());
            }else{
                loginResponse = new LoginResponse("Password MisMatch", LocalDateTime.now());
            }
        }
        return loginResponse;
    }


    @Override
    public CreatePostResponse createPost(PostDto postDto) {
        Post post = new Post();
        User user = findUserById(postDto.getUser_id());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setSlug(createSlug(postDto.getTitle()));
        post.setImage(postDto.getImage());
        post.setUser(user);
        postRepository.save(post);
        return new CreatePostResponse("success", LocalDateTime.now(), post);
    }

    @Override
    public CommentResponse createComment(long user_id, long post_id, CommentDto commentDto) {
        Comment comment = new Comment();
        Post post = findPostById(post_id);
        User user =  findUserById(user_id);
        comment.setComment(commentDto.getComment());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return new CommentResponse("success", comment,LocalDateTime.now(),post);
    }

    @Override
    public LikeResponse like(long user_id, long post_id, LikeDto likeDto) {
        Like like = new Like();
        Post post = findPostById(post_id);
        User user = findUserById(user_id);
        LikeResponse likeResponse;

        Like duplicated = likeRepository.findLikeByUserIdAndPostId(user_id, post_id);
        if(duplicated == null){
            like.setLiked(likeDto.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            List<Like> likeList = likeRepository.likeList(post_id);
            likeResponse = new LikeResponse("success", LocalDateTime.now(), post, like, likeList.size());
        }else{
            likeRepository.delete(duplicated);
            throw new PostAlreadyLikedException("This post is now unliked");
        }

        return likeResponse;
    }

    @Override
    public SearchCommentResponse searchComment(String keyword) {
       List<Comment> commentList = commentRepository.findByCommentContainingIgnoreCase(keyword);
        return new SearchCommentResponse("success", LocalDateTime.now(), commentList);
    }

    @Override
    public SearchPostResponse searchPost(String keyword) {
        List<Post> postList = postRepository.findByTitleContainingIgnoreCase(keyword);
        return new SearchPostResponse("success", LocalDateTime.now(),postList);
    }



}
