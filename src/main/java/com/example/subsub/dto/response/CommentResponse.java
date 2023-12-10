//package com.example.subsub.dto.response;
//
//import com.example.subsub.domain.Comment;
//import com.example.subsub.domain.Post;
//import com.example.subsub.domain.User;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import lombok.Getter;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//public class CommentResponse {
//
//    private Long commentId;
//    private String userId;
//    private Integer postId;
//    private String text;
//    private LocalDateTime createdAt;
//    private Boolean isSecret;
//
//    public CommentResponse(Comment comment){
//        commentId = comment.getCommentId();
//        userId = comment.getUser().getUserId();
//        postId = comment.getPost().getPostId();
//        text = comment.getText();
//        createdAt = comment.getCreatedAt();
//        isSecret = comment.isSecret();
//
//    }
//}
