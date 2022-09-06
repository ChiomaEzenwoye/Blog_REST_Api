package com.example.restblog.repository;

import com.example.restblog.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query (value = "SELECT * FROM likes WHERE user_id =?1, AND Post_id =?2", nativeQuery = true)
    Like findLikeByUserIdAndPostId(long user_id, long post_id);

   @Query(value = "SELECT * FROM likes WHERE post_id=?1", nativeQuery = true)
    List<Like> likeList(long post_id);
}
