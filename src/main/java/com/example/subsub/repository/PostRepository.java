package com.example.subsub.repository;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findTop3ByUserOrderByCreatedAtDesc(User user);
    Boolean existsByPostId(Integer postId);
    List<Post> findAllByLocationOrderByIsCloseAscCreatedAtDesc(String location);
    Optional<Post> findByPostId(Integer postId);
    void deleteByPostId(Integer postId);
    List<Post> findTop8ByIsCloseOrderByCreatedAtDesc(boolean isClose);
    List<Post> findTop8ByLocationStartingWithOrderByCreatedAtDesc(String prefix);
    List<Post> findAllByLocationStartingWithOrderByCreatedAtDesc(String prefix);

}
