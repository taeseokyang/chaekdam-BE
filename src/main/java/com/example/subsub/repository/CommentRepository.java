//package com.example.subsub.repository;
//
//import com.example.subsub.domain.Comment;
//import com.example.subsub.domain.Post;
//import com.example.subsub.domain.User;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CommentRepository extends JpaRepository<Comment, Integer> {
//    void deleteByCommentId(Integer commentId);
//    List<Comment> findAllByUser(User user);
//    Integer countByPost(Post post);
//}